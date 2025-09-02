package com.akcay.justwatch.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.CastItemView
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.util.Constants
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun MovieDetailScreen(
    navigateBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MovieDetailScreenContent(
        uiState = uiState,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenContent(
    uiState: MovieDetailUiState,
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JWTopAppBar(
                backNavigation = true,
                onBackClick = navigateBack,
            )
        },
    ) {
        it
        JWLoadingView(isLoading = uiState.loadingState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.clickable {
                        // TODO: exoplayer for trailer
                    },
                    contentAlignment = Alignment.Center,
                ) {
                    SubcomposeAsyncImage(
                        model = "${Constants.BASE_IMAGE_URL}${uiState.movieDetail?.posterPath}",
                        contentDescription = null,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                            }
                        },
                        contentScale = ContentScale.FillWidth,
                    )
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .zIndex(1f),
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "play button",
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    uiState.movieDetail?.genres?.forEach { genre ->
                        Text(text = genre.name)
                    }
                }

                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = uiState.movieDetail?.originalTitle ?: "",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = uiState.movieDetail?.overview ?: "",
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "Cast:",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )

                LazyRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    content = {
                        uiState.movieCast?.cast?.let { casts ->
                            items(casts.size) { position ->
                                CastItemView(
                                    imagePath = "${Constants.BASE_IMAGE_URL}${casts[position].profilePath}",
                                    castName = casts[position].name,
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
@Preview
fun MovieDetailScreenPreview() {
    JustWatchTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailUiState()
        )
    }
}
