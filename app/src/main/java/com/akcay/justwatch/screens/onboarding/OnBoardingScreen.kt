package com.akcay.justwatch.screens.onboarding

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.LoaderIntro
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.OnBoardingDataWithLottie
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.util.Constants
import com.akcay.justwatch.ui.theme.JustWatchTheme
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    onComplete: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {

    OnBoardingScreenContent(
        onComplete = {
            viewModel.saveOnBoardingVisibility()
            onComplete.invoke()
        }
    )
}

@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Scaffold(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                contentPadding = PaddingValues(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                state = pagerState,
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(395.dp),
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null,
                    )

                    Text(
                        text = pages[page].title,
                        modifier = Modifier.padding(top = 50.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    Text(
                        text = pages[page].description,
                        modifier = Modifier
                            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        color = Color.Gray,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            PagerIndicator(
                modifier = Modifier.padding(bottom = 40.dp),
                size = pages.size,
                currentPage = pagerState.currentPage,
            )

            BottomSection(
                modifier = Modifier
                    .height(90.dp)
                    .padding(bottom = 20.dp),
                pages = pages,
                pagerState = pagerState,
                onNext = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage.plus(1))
                    }
                },
                onComplete = onComplete
            )
        }
    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(size) {
            IndicatorView(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorView(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 55.dp else 20.dp, label = "")

    Box(
        modifier = Modifier
            .padding(1.dp)
            .height(4.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color.White else Color.Gray.copy(alpha = 0.5f),
            ),
    )
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    pages: List<OnBoardingDataWithLottie>,
    pagerState: PagerState,
    onNext: () -> Unit,
    onComplete: () -> Unit
) {
    val isLastPage = pages.lastIndex == pagerState.currentPage

    val skipButtonWeight by animateFloatAsState(
        targetValue = if (isLastPage) 0f else 1f,
        animationSpec = tween(durationMillis = 600),
        label = "",
    )

    val skipButtonAlpha by animateFloatAsState(
        targetValue = if (isLastPage) 0f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "",
    )

    val nextButtonWeight by animateFloatAsState(
        targetValue = if (isLastPage) 3f else 2f,
        animationSpec = tween(durationMillis = 600),
        label = "",
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        if (skipButtonWeight > 0f) {
            JWButton(
                modifier = modifier
                    .weight(skipButtonWeight)
                    .padding(end = 10.dp)
                    .alpha(skipButtonAlpha),
                text = "Skip",
                textColor = Color.Gray,
                backgroundColor = Color.LightGray,
                onClick = {

                }
            )
        }
        JWButton(
            modifier = modifier
                .weight(nextButtonWeight),
            text = if (isLastPage) "Get Started" else "Next",
            textColor = Color.Black,
            backgroundColor = Color.White,
            onClick = {
                if (isLastPage) {
                    onComplete()
                } else {
                    onNext()
                }
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun OnBoardingScreenPreview() {
    JustWatchTheme {
        OnBoardingScreenContent()
    }
}
