package com.akcay.justwatch.screens.movies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import kotlinx.coroutines.launch
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTabRow
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.internal.component.ListMovieItemModel
import com.akcay.justwatch.internal.component.TabRowItem
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.NavigationScaffold
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    onCardClick: (Long, String) -> Unit,
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MoviesScreenContent(
        uiState = uiState,
        onCardClick = onCardClick,
        loadMore = viewModel::loadMore,
        onTabChange = viewModel::onTabChanged,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
    )
}

@Composable
fun MoviesScreenContent(
    uiState: MoviesUiState,
    onCardClick: (Long, String) -> Unit = {_, _ -> },
    loadMore: () -> Unit = {},
    onTabChange: (TabRowItem) -> Unit = {},
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    // Sync pager state with selected tab
    LaunchedEffect(uiState.selectedTab) {
        val targetPage = when (uiState.selectedTab) {
            TabRowItem.ACTIVE -> 0
            TabRowItem.TOP_RATED -> 1
        }
        if (pagerState.currentPage != targetPage) {
            pagerState.animateScrollToPage(targetPage)
        }
    }

    // Sync selected tab with pager state
    LaunchedEffect(pagerState.currentPage) {
        val targetTab = when (pagerState.currentPage) {
            0 -> TabRowItem.ACTIVE
            1 -> TabRowItem.TOP_RATED
            else -> TabRowItem.ACTIVE
        }
        if (uiState.selectedTab != targetTab) {
            onTabChange(targetTab)
        }
    }

    NavigationScaffold(
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        topBar = {
            JWTopAppBar(
                title = "Movies",
            )
        },
        content = {
            JWLoadingView(isLoading = uiState.loading) {
                Column(
                    modifier = Modifier
                      .fillMaxSize()
                      .padding(top = it.calculateTopPadding() + 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    JWTabRow(
                        items = listOf(TabRowItem.ACTIVE, TabRowItem.TOP_RATED),
                        onTabChange = { tab ->
                            coroutineScope.launch {
                                val targetPage = when (tab) {
                                    TabRowItem.ACTIVE -> 0
                                    TabRowItem.TOP_RATED -> 1
                                }
                                pagerState.animateScrollToPage(targetPage)
                            }
                        },
                    )

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = false,
                    ) { page ->
                        val currentMovieList = when (page) {
                            0 -> uiState.movieList
                            1 -> uiState.topRatedMovieList
                            else -> emptyList()
                        }

                        MovieGrid(
                            movieList = currentMovieList,
                            onCardClick = onCardClick,
                            loadMore = loadMore
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun MovieGrid(
    movieList: List<com.akcay.justwatch.screens.movies.domain.model.MovieUIModel>,
    onCardClick: (Long, String) -> Unit,
    loadMore: () -> Unit
) {
    val gridState = rememberLazyGridState()
    val isOnBottom by remember {
        derivedStateOf {
            with(gridState.layoutInfo) {
                visibleItemsInfo.lastOrNull()?.index == totalItemsCount - 1
            }
        }
    }

    LaunchedEffect(isOnBottom) {
        if (isOnBottom) loadMore()
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        items(
            items = movieList,
            key = { item -> item.id }
        ) { item ->
            ListMovieItem(
                model = ListMovieItemModel(
                    imageUrl = item.image,
                    itemId = item.id,
                    movieName = item.title,
                    voteAverage = item.voteAverage,
                ),
                onCardClicked = onCardClick,
            )
        }
    }
}

@Preview
@Composable
fun MoviesScreenPreview() {
    JustWatchTheme {
        MoviesScreenContent(
            uiState = MoviesUiState(),
        )
    }
}
