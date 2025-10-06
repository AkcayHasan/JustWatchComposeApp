package com.akcay.justwatch.internal.navigation

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.akcay.justwatch.internal.ext.navigateToTab
import com.akcay.justwatch.screens.account.AccountScreen
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.favourite.FavouriteScreen
import com.akcay.justwatch.screens.movies.ui.MoviesScreen

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    val isTabSelected: (MainDestination) -> Boolean = { mainDestination ->
        navController.currentDestination?.hierarchy?.any { it.hasRoute(mainDestination::class) } == true
    }

    composable<MainDestination.Movies> {
        MoviesScreen(
            onCardClick = { id, title ->
                navController.navigate(route = MainDestination.MovieDetail(id))
            },
            isSelected = isTabSelected,
            navigateToTab = { navController.navigateToTab(it) },
        )
    }
    composable<MainDestination.Account> {
        AccountScreen(
            isSelected = isTabSelected,
            navigateToTab = { navController.navigateToTab(it) },
            navigateToLogin = {
                navController.navigate(AppDestination.Login) {
                    popUpTo(0) { inclusive = true }
                }
            },
        )
    }
    composable<MainDestination.Favourite> {
        FavouriteScreen(
            isSelected = isTabSelected,
            navigateToTab = { navController.navigateToTab(it) },
            onCardClick = { id, title ->
                navController.navigate(route = MainDestination.MovieDetail(id))
            },
            navigateToLogin = {
                navController.navigate(AppDestination.Login) {
                    popUpTo(0) { inclusive = true }
                }
            },
            navigateToRegister = {
                navController.navigate(Destinations.Register) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
    composable<MainDestination.MovieDetail> {
        MovieDetailScreen(
            navigateBack = {
                navController.popBackStack()
            },
        )
    }
}
