package com.akcay.justwatch.screens.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.internal.component.JWAuthRequiredDialog
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
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: FavouriteViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is FavouriteScreenViewModelEvent.NavigateToLogin -> navigateToLogin()
                is FavouriteScreenViewModelEvent.NavigateToRegister -> navigateToRegister()
            }
        }
    }

    FavouriteScreenContent(
        uiState = uiState,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        onCardClick = onCardClick,
        onRemoveFavorite = viewModel::removeFromFavorites,
        onDismissAuthDialog = { viewModel.sendEvent(FavouriteScreenViewEvent.DismissAuthDialog) },
        onLoginClick = { viewModel.sendEvent(FavouriteScreenViewEvent.NavigateToLogin) },
        onRegisterClick = { viewModel.sendEvent(FavouriteScreenViewEvent.NavigateToRegister) }
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
    onDismissAuthDialog: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
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
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "No favorite movies",
                                modifier = Modifier.size(80.dp),
                                tint = JustWatchTheme.colors.onSurfaceVariant
                            )
                            Text(
                                text = "Henüz favori filminiz yok",
                                style = JustWatchTheme.typography.h2,
                                color = JustWatchTheme.colors.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            Text(
                                text = "Beğendiğiniz filmleri favorilere ekleyerek burada görebilirsiniz",
                                style = JustWatchTheme.typography.body,
                                color = JustWatchTheme.colors.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp)
                            )
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
    
    if (uiState.showAuthRequiredDialog) {
        JWAuthRequiredDialog(
            onDismiss = onDismissAuthDialog,
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick
        )
    }
}

@Preview
@Composable
fun FavouriteScreenContentPreview() {
    JustWatchTheme {
        FavouriteScreenContent(
            uiState = FavouriteUiState()
        )
    }
}
