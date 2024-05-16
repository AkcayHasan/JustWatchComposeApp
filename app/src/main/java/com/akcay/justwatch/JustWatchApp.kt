package com.akcay.justwatch

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.home.HomeSections
import com.akcay.justwatch.screens.home.addHomeGraph
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme
import com.akcay.justwatch.util.ClickActions

@Composable
fun JustWatchApp() {
    JustWatchTheme {
        val justWatchNavController = rememberJustWatchNavController()

        val startDestination = if() {

        } else {

        }


        NavHost(
            navController = justWatchNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            justWatchNavGraph(
                upPress = justWatchNavController::upPress,
                clickAction = justWatchNavController::clickActions
            )
        }
    }
}

private fun NavGraphBuilder.justWatchNavGraph(
    upPress: () -> Unit,
    clickAction: (ClickActions, Bundle?) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.POPULAR_MOVIES.route
    ) {
        addHomeGraph()
    }
    composable(route = Screen.OnBoarding.route) {
        OnBoardingScreen(buttonClicked = clickAction)
    }
    composable(
        route = Screen.MovieDetail.route,
        arguments = Screen.MovieDetail.navArgument
    ) {backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val movieId = arguments.getLong("movieId")
        MovieDetailScreen(upPress = upPress, movieId = movieId)
    }
}