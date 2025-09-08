package com.akcay.justwatch.screens.movies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    viewModel: MoviesViewModel = hiltViewModel(),
    onCardClick: (Long, String) -> Unit,
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MoviesScreenContent(
        uiState = uiState,
        onCardClick = onCardClick,
        loadMore = viewModel::loadMore,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
    )
}

@Composable
fun MoviesScreenContent(
    uiState: MoviesUiState,
    onCardClick: (Long, String) -> Unit = {_, _ -> },
    loadMore: () -> Unit = {},
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
    onTabChange: (TabRowItem) -> Unit = {},
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
                        onTabChange = onTabChange,
                    )
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp),
                    ) {
                        items(
                            items = uiState.movieList,
                            key = { item -> item.id }
                        ) { item ->
                            ListMovieItem(
                                model = ListMovieItemModel(
                                    imageUrl = item.image,
                                    itemId = item.id,
                                    movieName = item.title,
                                    voteAverage = 4.326,
                                ),
                                onCardClicked = onCardClick,
                            )
                        }
                    }
                }
            }
        },
    )
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
