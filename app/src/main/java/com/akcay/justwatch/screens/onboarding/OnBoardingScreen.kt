package com.akcay.justwatch.screens.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.akcay.justwatch.R
import com.akcay.justwatch.data.model.LoaderIntro
import com.akcay.justwatch.data.model.OnBoardingData
import com.akcay.justwatch.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    buttonClicked: (OnBoardingTextActions) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnBoardingData(
            R.raw.stage1,
            "Stage1",
            "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
        ),
        OnBoardingData(
            R.raw.stage2,
            "Stage2",
            "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface. Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
        ),
        OnBoardingData(
            R.raw.stage1,
            "Stage3",
            "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
        )
    )

    val pagerState = rememberPagerState(
        pageCount = {
            pages.size
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(top = 50.dp, end = 20.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            if (pagerState.currentPage != pages.size.minus(1)) {
                OnBoardingTextButtons(
                    text = "Skip",
                    modifier = Modifier.clickable {
                        buttonClicked.invoke(OnBoardingTextActions.SKIP)
                    }
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.padding(top = 100.dp, start = 10.dp, end = 10.dp),
            state = pagerState
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoaderIntro(
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    file = pages[page].lottieFile
                )

                Text(
                    text = pages[page].title,
                    modifier = Modifier.padding(top = 50.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineSmall,
                )

                Text(
                    text = pages[page].description,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .height(150.dp),
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }


        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            PagerIndicator(
                modifier = Modifier.padding(bottom = 200.dp),
                pages.size,
                pagerState.currentPage
            )
        }


        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomSection(pages, pagerState) { actions ->
                when(actions) {
                    OnBoardingTextActions.NEXT -> {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage.plus(1))
                        }
                    }
                    OnBoardingTextActions.PREVIOUS -> {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage.minus(1))
                        }
                    }
                    OnBoardingTextActions.GET_STARTED -> {
                        buttonClicked.invoke(OnBoardingTextActions.GET_STARTED)
                    }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(size) {
            IndicatorView(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorView(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp, label = "")

    Box(
        modifier = Modifier
            .padding(1.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color.Red else Color.Gray.copy(alpha = 0.5f)
            )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomSection(
    pages: List<OnBoardingData>,
    pagerState: PagerState,
    clickActions: (OnBoardingTextActions) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalArrangement = if (pages.lastIndex == pagerState.currentPage) {
            Arrangement.Center
        } else if (pagerState.currentPage != Constants.ZERO){
            Arrangement.SpaceBetween
        } else {
            Arrangement.End
        }
    ) {
        if (pages.lastIndex == pagerState.currentPage) {
            OutlinedButton(onClick = {
                clickActions.invoke(OnBoardingTextActions.GET_STARTED)
            }, shape = RoundedCornerShape(size = 50.dp)) {
                Text(
                    text = "Get Started",
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 40.dp),
                    color = Color.LightGray
                )
            }
        } else {
            if (pagerState.currentPage != Constants.ZERO) {
                OnBoardingTextButtons(
                    text = "Previous",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable {
                            clickActions.invoke(OnBoardingTextActions.PREVIOUS)
                        })
            }
            OnBoardingTextButtons(
                text = "Next",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        clickActions.invoke(OnBoardingTextActions.NEXT)
                    },
            )
        }
    }
}

enum class OnBoardingTextActions {
    SKIP,
    NEXT,
    PREVIOUS,
    GET_STARTED
}

@Composable
fun OnBoardingTextButtons(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.Black,
        fontSize = 18.sp,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingScreenPreview() {
    val pages = listOf(
        OnBoardingData(
            R.raw.stage1,
            "Stage1", "Description1"
        ),
        OnBoardingData(
            R.raw.stage2,
            "Stage2", "Description2"
        ),
        OnBoardingData(
            R.raw.stage1,
            "Stage3", "Description1"
        )
    )

    val pagerState = rememberPagerState(
        pageCount = {
            pages.size
        }
    )

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(state = pagerState) { page ->

            }
        }
    }
}