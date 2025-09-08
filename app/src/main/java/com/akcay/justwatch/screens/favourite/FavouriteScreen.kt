package com.akcay.justwatch.screens.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.internal.component.ListMovieItemModel
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.NavigationScaffold
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun FavouriteScreen(
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
    onCardClick: (Long, String) -> Unit,
    viewModel: FavouriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    FavouriteScreenContent(
        uiState = uiState,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        onCardClick = onCardClick,
        onRemoveFavorite = viewModel::removeFromFavorites
    )
}

@Composable
fun FavouriteScreenContent(
    uiState: FavouriteUiState,
    modifier: Modifier = Modifier,
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
    onCardClick: (Long, String) -> Unit = { _, _ -> },
    onRemoveFavorite: (Long) -> Unit = {},
) {
    NavigationScaffold(
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        topBar = {
            JWTopAppBar(
                title = "Favourite Movies",
            )
        },
        content = { paddingValues ->
            JWLoadingView(isLoading = uiState.loading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding() + 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (uiState.favoriteMovies.isEmpty() && !uiState.loading) {
                        // Empty state
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Empty state UI can be added here
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(16.dp),
                        ) {
                            items(
                                items = uiState.favoriteMovies,
                                key = { item -> item.id }
                            ) { favoriteMovie ->
                                ListMovieItem(
                                    model = ListMovieItemModel(
                                        imageUrl = favoriteMovie.posterPath,
                                        itemId = favoriteMovie.id,
                                        movieName = favoriteMovie.title,
                                        voteAverage = favoriteMovie.voteAverage,
                                    ),
                                    onCardClicked = onCardClick,
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun FavouriteScreenPreview() {
    JustWatchTheme {
        FavouriteScreenContent(
            uiState = FavouriteUiState()
        )
    }
}
