package com.akcay.justwatch.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import org.koin.androidx.compose.koinViewModel
import coil.compose.AsyncImage
import com.akcay.justwatch.R
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.internal.component.CastItemView
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.component.JWExpandableText
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.RatingChip
import com.akcay.justwatch.internal.util.Constants
import com.akcay.justwatch.ui.theme.JustWatchTheme
import java.util.Locale

@Composable
fun MovieDetailScreen(
    navigateBack: () -> Unit,
    viewModel: MovieDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MovieDetailScreenContent(
        uiState = uiState,
        navigateBack = navigateBack,
        onFavoriteClicked = {
            viewModel.sendEvent(MovieDetailScreenViewEvent.FavoriteIconClicked)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenContent(
    uiState: MovieDetailUiState,
    navigateBack: () -> Unit = {},
    onFavoriteClicked: () -> Unit = {},
) {
    val url = remember(uiState.movieDetail?.posterPath) {
        "${Constants.BASE_IMAGE_URL}${uiState.movieDetail?.posterPath}"
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JWTopAppBar(
                backNavigation = true,
                onBackClick = navigateBack,
                title = uiState.movieDetail?.originalTitle.orEmpty(),
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                onFavoriteClicked()
                            },
                        imageVector = if (uiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (uiState.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (uiState.isFavorite) Color.Red else JustWatchTheme.colors.onSurface,
                    )
                },
            )
        },
    ) {
        JWLoadingView(isLoading = uiState.loadingState) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                InfoSection(model = uiState.movieDetail)

                RatingChip(
                    text = String.format(Locale.US, "%.1f", uiState.movieDetail?.voteAverage),
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    JWButton(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "Play",
                        backgroundColor = JustWatchTheme.colors.primaryContainer,
                        textColor = JustWatchTheme.colors.onPrimaryContainer,
                        icon = Icons.Default.PlayArrow,
                        iconColor = JustWatchTheme.colors.onPrimaryContainer,
                        onClick = {

                        }
                    )

                    Icon(
                        modifier = Modifier.size(40.dp).background(
                            color = JustWatchTheme.colors.primaryContainer,
                            shape = RoundedCornerShape(50.dp)
                        ).padding(10.dp),
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = JustWatchTheme.colors.onPrimaryContainer
                    )
                }

                StorySection(modifier = Modifier.align(Alignment.Start), state = uiState)
            }
        }
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
    model: MovieDetailResponse?,
) {
    Row(
        modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = model?.releaseDate?.substringBefore("-").orEmpty(),
            color = JustWatchTheme.colors.secondary,
        )
        VerticalDivider(
            modifier = Modifier
                .height(24.dp)
                .padding(horizontal = 10.dp),
            thickness = 2.dp,
            color = JustWatchTheme.colors.secondary,
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "148 Minutes",
            color = JustWatchTheme.colors.secondary,
        )
        VerticalDivider(
            modifier = Modifier
                .height(24.dp)
                .padding(horizontal = 10.dp),
            thickness = 2.dp,
            color = JustWatchTheme.colors.secondary,
        )
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,
        )
        Text(
            text = model?.genres?.firstOrNull()?.name ?: "",
            color = JustWatchTheme.colors.secondary,
        )
    }
}

@Composable
private fun ColumnScope.StorySection(
    modifier: Modifier = Modifier,
    state: MovieDetailUiState,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Story Line",
            style = JustWatchTheme.typography.h2,
        )
        JWExpandableText(
            initialText = state.movieDetail?.overview ?: "",
            maxLine = 2
        )

        Text(
            text = "Cast and Crew",
            style = JustWatchTheme.typography.h2,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            content = {
                state.movieCast?.cast?.let { casts ->
                    items(casts, key = { it.id }) { cast ->
                        CastItemView(
                            imagePath = "${Constants.BASE_IMAGE_URL}${cast.profilePath}",
                            castName = cast.name,
                            knownForDepartment = cast.knownForDepartment
                        )
                    }
                }
            },
        )
    }
}

@Composable
@Preview
fun MovieDetailScreenPreview() {
    JustWatchTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailUiState(),
        )
    }
}
