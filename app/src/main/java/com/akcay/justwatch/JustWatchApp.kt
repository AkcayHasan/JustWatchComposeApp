package com.akcay.justwatch

import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.akcay.justwatch.screens.home.HomeViewModel
import com.akcay.justwatch.screens.login.addLoginGraph
import com.akcay.justwatch.screens.onboarding.ClickActions
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.screens.movies.MoviesScreen
import com.akcay.justwatch.screens.profile.ProfileScreen
import com.akcay.justwatch.screens.favourite.UpcomingMoviesScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val LOGIN_ROUTE = "login"
    const val ONBOARDING_ROUTE = "onboarding"
}

fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    onMovieSelected: (Long) -> Unit
) {
    composable(BottomNavSections.POPULAR_MOVIES.route) {
        MoviesScreen(onCardClick = onMovieSelected)
    }
    composable(BottomNavSections.UPCOMING_MOVIES.route) {
        UpcomingMoviesScreen()
    }
    composable(BottomNavSections.PROFILE.route) {
        ProfileScreen()
    }
}

enum class BottomNavSections(
    @StringRes val titleResId: Int,
    val drawableResId: ImageVector,
    val route: String
) {
    POPULAR_MOVIES(
        R.string.popular_movies_title,
        Icons.Default.Settings,
        Screen.PopularMovies.route
    ),
    UPCOMING_MOVIES(
        R.string.upcoming_movies_title,
        Icons.Default.Settings,
        Screen.UpcomingMovies.route
    ),
    PROFILE(R.string.profile_title, Icons.Default.Settings, Screen.Profile.route)
}

@Composable
fun JustWatchApp(viewModel: HomeViewModel = hiltViewModel()) {
    JustWatchTheme {
        val justWatchNavController = rememberNavController()

        var startDestination by remember {
            mutableStateOf(MainDestinations.LOGIN_ROUTE)
        }

        LaunchedEffect(Unit) {
            val shouldShowOnboarding = viewModel.storeManager.shouldOnBoardingVisible()
            if (shouldShowOnboarding) {
                startDestination = MainDestinations.ONBOARDING_ROUTE
            }
        }


        val screens = listOf(
            BottomNavSections.POPULAR_MOVIES,
            BottomNavSections.UPCOMING_MOVIES,
            BottomNavSections.PROFILE
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
                            justWatchNavController.navigate(it.route) {
                                launchSingleTop = true
                            }
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
                    startDestination = startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
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
        startDestination = BottomNavSections.POPULAR_MOVIES.route,
    ) {
        addHomeGraph(onMovieSelected = { id ->
            navController.navigate(
                route = Screen.MovieDetail.createRoute(id)
            )
        })
    }
    navigation(
        route = MainDestinations.LOGIN_ROUTE,
        startDestination = Screen.Login.route
    ) {
        addLoginGraph(
            onLoginClick = { email, password ->

            },
            onRegisterClick = {

            },
            onForgotPasswordClick = {

            },
            onGuestClick = {
                navController.navigate(
                    route = MainDestinations.HOME_ROUTE
                )
            },
        )
    }
    composable(route = MainDestinations.ONBOARDING_ROUTE) {
        OnBoardingScreen(
            buttonClicked = { clickActions, args ->
                when (clickActions) {
                    ClickActions.GET_STARTED_ONBOARDING -> {
                        navController.navigate(MainDestinations.LOGIN_ROUTE)
                    }

                    ClickActions.NEXT_ONBOARDING -> {}
                    ClickActions.SKIP_ONBOARDING -> {
                        navController.navigate(MainDestinations.LOGIN_ROUTE)
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