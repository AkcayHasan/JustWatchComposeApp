package com.akcay.justwatch

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.home.HomeScreen
import com.akcay.justwatch.screens.popular.PopularMoviesScreen
import com.akcay.justwatch.screens.settings.SettingsScreen
import com.akcay.justwatch.screens.upcoming.UpcomingMoviesScreen

@Composable
fun JustWatchNavigation() {
    HomeScreen()
}

@Composable
fun JustWatchNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PopularMovies.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Screen.MovieDetail.route, arguments = Screen.MovieDetail.navArgument) {
            MovieDetailScreen(navController = navController)
        }

        composable(route = Screen.PopularMovies.route) {
            PopularMoviesScreen(navController = navController)
        }

        composable(route = Screen.UpcomingMovies.route) {
            UpcomingMoviesScreen()
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }
    }


}