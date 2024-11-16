package com.akcay.justwatch

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import com.akcay.justwatch.internal.component.JWBottomNavBar
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.internal.navigation.defaultEnterTransition
import com.akcay.justwatch.internal.navigation.defaultExitTransition
import com.akcay.justwatch.internal.navigation.defaultPopEnterTransition
import com.akcay.justwatch.internal.navigation.defaultPopExitTransition
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.onboarding.ClickActions
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.screens.movies.MoviesScreen
import com.akcay.justwatch.screens.profile.ProfileScreen
import com.akcay.justwatch.screens.favourite.UpcomingMoviesScreen
import com.akcay.justwatch.screens.forgotpassword.ForgotPasswordScreen
import com.akcay.justwatch.screens.home.HomeScreen
import com.akcay.justwatch.screens.login.LoginScreen
import com.akcay.justwatch.screens.register.RegisterScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun JustWatchApp(viewModel: JustWatchViewModel = hiltViewModel(), startDestination: String) {
  JustWatchTheme {
    val justWatchNavController = rememberJustWatchNavController()

    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      NavHost(
        navController = justWatchNavController.navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
      ) {
        justWatchNavGraph(justWatchNavController)
      }
    }
  }
}

object MainDestinations {
  const val HOME_ROUTE = "home"
  const val LOGIN_ROUTE = "login"
  const val ONBOARDING_ROUTE = "onboarding"
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
  PROFILE(
    R.string.profile_title,
    Icons.Default.Settings,
    Screen.Profile.route
  )
}

@Composable
fun rememberJustWatchNavController(
  navController: NavHostController = rememberNavController(),
) = remember(navController) {
  JustWatchNavController(navController = navController)
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

fun NavGraphBuilder.addLoginGraph(
  justWatchNavController: JustWatchNavController
) {
  composable(
    Screen.Login.route,
    exitTransition = defaultExitTransition(),
    popEnterTransition = defaultPopEnterTransition()
  ) {
    LoginScreen(
      navigate = { route ->
        justWatchNavController.navigate(route)
      },
      navigateAndPopUp = { route, popupName ->
        justWatchNavController.navigateAndPopUp(route, popupName)
      }
    )
  }
  composable(
    Screen.Register.route,
    enterTransition = defaultEnterTransition(),
    exitTransition = defaultExitTransition(),
    popExitTransition = defaultPopExitTransition()
  ) {
    RegisterScreen(
      navigateAndPopUp = { route, popupName ->
        justWatchNavController.navigateAndPopUp(route, popupName)
      }
    )
  }
  composable(Screen.ForgotPassword.route) {
    ForgotPasswordScreen()
  }
}

private fun NavGraphBuilder.justWatchNavGraph(
  justWatchNavController: JustWatchNavController
) {
  composable(
    MainDestinations.HOME_ROUTE,
    exitTransition = defaultExitTransition(),
    popEnterTransition = defaultPopEnterTransition()
  ) {
    HomeScreen { id ->
      justWatchNavController.navigate(
        route = Screen.MovieDetail.createRoute(id)
      )
    }
  }
  navigation(
    route = MainDestinations.LOGIN_ROUTE,
    startDestination = Screen.Login.route
  ) {
    addLoginGraph(justWatchNavController)
  }
  composable(route = MainDestinations.ONBOARDING_ROUTE) {
    OnBoardingScreen(
      buttonClicked = { clickActions, args ->
        when (clickActions) {
          ClickActions.GET_STARTED_ONBOARDING -> {
            justWatchNavController.navigateAndPopUp(
              MainDestinations.LOGIN_ROUTE,
              MainDestinations.ONBOARDING_ROUTE
            )
          }

          ClickActions.NEXT_ONBOARDING -> {}
          ClickActions.SKIP_ONBOARDING -> {
            justWatchNavController.navigateAndPopUp(
              MainDestinations.LOGIN_ROUTE,
              MainDestinations.ONBOARDING_ROUTE
            )
          }

          ClickActions.PREVIOUS_ONBOARDING -> {}
        }
      }
    )
  }
  composable(
    route = Screen.MovieDetail.route,
    arguments = Screen.MovieDetail.navArgument,
    enterTransition = defaultEnterTransition(),
    exitTransition = defaultExitTransition(),
    popExitTransition = defaultPopExitTransition()
  ) { backStackEntry ->
    val arguments = requireNotNull(backStackEntry.arguments)
    val movieId = arguments.getLong("movieId")
    MovieDetailScreen(
      upPress = {
        justWatchNavController.popUp()
      }, movieId = movieId
    )
  }
}