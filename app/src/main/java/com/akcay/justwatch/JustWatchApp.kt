package com.akcay.justwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.akcay.justwatch.component.JWBottomNavBar
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.home.BottomNavSections
import com.akcay.justwatch.screens.home.HomeViewModel
import com.akcay.justwatch.screens.home.addHomeGraph
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme
import com.akcay.justwatch.util.ClickActions
import kotlinx.coroutines.runBlocking

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val ONBOARDING_ROUTE = "onboarding"
}

@Composable
fun JustWatchApp(viewModel: HomeViewModel = hiltViewModel()) {
    JustWatchTheme {
        val justWatchNavController = rememberNavController()
        val scope = rememberCoroutineScope()

        var startDestination by remember {
            mutableStateOf(MainDestinations.HOME_ROUTE)
        }

        LaunchedEffect(Unit) {
            runBlocking {
                val shouldShowOnboarding = viewModel.storeManager.shouldOnBoardingVisible()
                if (shouldShowOnboarding) {
                    startDestination = MainDestinations.ONBOARDING_ROUTE
                }
            }
        }


        val screens = listOf(
            BottomNavSections.POPULAR_MOVIES,
            BottomNavSections.UPCOMING_MOVIES,
            BottomNavSections.SETTINGS
        )

        val showBottomBar =
            justWatchNavController.currentBackStackEntryAsState().value?.destination?.route in screens.map {
                it.route
            }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (showBottomBar) {
                    JWBottomNavBar(
                        modifier = Modifier,
                        navController = justWatchNavController,
                        selectedItem = {
                            justWatchNavController.navigate(it.route)
                        },
                        items = screens
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                NavHost(
                    navController = justWatchNavController,
                    startDestination = startDestination
                ) {
                    justWatchNavGraph(
                        navController = justWatchNavController
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.justWatchNavGraph(
    navController: NavHostController
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = BottomNavSections.POPULAR_MOVIES.route
    ) {
        addHomeGraph(onMovieSelected = { id ->
            navController.navigate(
                route = Screen.MovieDetail.createRoute(id)
            )
        })
    }
    composable(route = MainDestinations.ONBOARDING_ROUTE) {
        OnBoardingScreen(
            buttonClicked = { clickActions, args ->
                when (clickActions) {
                    ClickActions.GET_STARTED_ONBOARDING -> {
                        navController.navigate(MainDestinations.HOME_ROUTE)
                    }

                    ClickActions.NEXT_ONBOARDING -> {}
                    ClickActions.SKIP_ONBOARDING -> {
                        navController.navigate(MainDestinations.HOME_ROUTE)
                    }

                    ClickActions.PREVIOUS_ONBOARDING -> {}
                }
            }
        )
    }
    composable(
        route = Screen.MovieDetail.route,
        arguments = Screen.MovieDetail.navArgument
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val movieId = arguments.getLong("movieId")
        MovieDetailScreen(
            upPress = {
                navController.navigateUp()
            }, movieId = movieId
        )
    }
}