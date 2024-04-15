package com.akcay.justwatch

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.home.HomeScreen
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.screens.onboarding.OnBoardingTextActions
import com.akcay.justwatch.screens.popular.PopularMoviesScreen
import com.akcay.justwatch.screens.settings.SettingsScreen
import com.akcay.justwatch.screens.upcoming.UpcomingMoviesScreen
import com.akcay.justwatch.util.DataStoreManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun JustWatchNavigation() {
    HomeScreen()
}

@Composable
fun JustWatchNavHost(
    navController: NavHostController,
    storeManager: DataStoreManager
) {
    val started: String
    runBlocking {
        val should = storeManager.shouldOnBoardingVisible()

        started = if (should) {
            Screen.PopularMovies.route
        } else {
            Screen.OnBoarding.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = started,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(buttonClicked = { actions ->
                when (actions) {
                    OnBoardingTextActions.SKIP -> {
                        navController.navigate(Screen.PopularMovies.createRoute())
                    }

                    OnBoardingTextActions.GET_STARTED -> {
                        navController.navigate(Screen.PopularMovies.createRoute())
                    }

                    else -> Unit
                }
            })
        }

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