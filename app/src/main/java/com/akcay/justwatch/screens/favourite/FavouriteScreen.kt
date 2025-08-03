package com.akcay.justwatch.screens.favourite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.NavigationScaffold
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun FavouriteScreen(
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
    viewModel: FavouriteViewModel = hiltViewModel(),
) {


    FavouriteScreenContent(
        isSelected = isSelected,
        navigateToTab = navigateToTab
    )
}

@Composable
fun FavouriteScreenContent(
    modifier: Modifier = Modifier,
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
) {
    NavigationScaffold(
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        topBar = {
            JWTopAppBar(
                title = "Favourite Movies",
            )
        },
        content = {

        }
    )
}

@Preview
@Composable
fun FavouriteScreenPreview() {
    JustWatchTheme {
        FavouriteScreenContent()
    }
}
