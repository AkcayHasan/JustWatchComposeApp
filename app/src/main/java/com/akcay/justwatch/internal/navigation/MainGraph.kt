package com.akcay.justwatch.internal.navigation

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.movies.ui.MoviesScreen

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    val isTabSelected: (MainDestination) -> Boolean = { mainDestination ->
        navController.currentDestination?.hierarchy?.any { it.hasRoute(mainDestination::class) } == true
    }


    composable<MainDestination.Movies> {
        MoviesScreen(
            onCardClick = { id ->
                navController.navigate(route = MainDestination.MovieDetail(id))
            },
            isSelected = isTabSelected,
            navigateToTab = {
                if (navController.currentDestination?.hasRoute(it::class) == true) return@MoviesScreen

                navController.navigate(it) {
                    popUpTo(MainDestination.Movies) {
                        inclusive = false
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = it == MainDestination.Movies
                }
            }
        )
    }
    composable<MainDestination.Search> {

    }
    composable<MainDestination.Favourite> {

    }
    composable<MainDestination.MovieDetail> {
        MovieDetailScreen(
            navigateBack = {
                navController.popBackStack()
            },
        )
    }
}
