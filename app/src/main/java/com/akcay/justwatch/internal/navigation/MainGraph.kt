package com.akcay.justwatch.internal.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.akcay.justwatch.JustWatchNavController
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.movies.ui.MoviesScreen

fun NavGraphBuilder.mainGraph(
    navController: JustWatchNavController,
) {
    composable<MainDestination.Movies> {
        MoviesScreen(
            onCardClick = { id ->
                navController.navigate(route = MainDestination.MovieDetail(id))
            },
        )
    }
    composable<MainDestination.Search> {

    }
    composable<MainDestination.Favourite> {

    }
    composable<MainDestination.MovieDetail> {
        MovieDetailScreen(
            navigateBack = {
                navController.popUp()
            },
        )
    }
}
