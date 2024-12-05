package com.akcay.justwatch

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.internal.navigation.defaultEnterTransition
import com.akcay.justwatch.internal.navigation.defaultExitTransition
import com.akcay.justwatch.internal.navigation.defaultPopEnterTransition
import com.akcay.justwatch.internal.navigation.defaultPopExitTransition
import com.akcay.justwatch.internal.util.LocalThemeManager
import com.akcay.justwatch.screens.detail.MovieDetailScreen
import com.akcay.justwatch.screens.onboarding.ClickActions
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen
import com.akcay.justwatch.screens.movies.MoviesScreen
import com.akcay.justwatch.screens.profile.ProfileScreen
import com.akcay.justwatch.screens.favourite.UpcomingMoviesScreen
import com.akcay.justwatch.screens.forgotpassword.ForgotPasswordScreen
import com.akcay.justwatch.screens.home.BottomNavSections
import com.akcay.justwatch.screens.home.HomeScreen
import com.akcay.justwatch.screens.login.LoginScreen
import com.akcay.justwatch.screens.register.RegisterScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun JustWatchApp(
  viewModel: JustWatchViewModel = hiltViewModel(),
  startDestination: String
) {
  val isDarkTheme by LocalThemeManager.current.isDarkThemeEnabled.collectAsState()

  JustWatchTheme(darkTheme = isDarkTheme) {
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

private fun NavGraphBuilder.justWatchNavGraph(
  justWatchNavController: JustWatchNavController
) {
  composable(
    MainDestinations.HOME_ROUTE,
    enterTransition = defaultEnterTransition(),
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

object MainDestinations {
  const val HOME_ROUTE = "home"
  const val LOGIN_ROUTE = "login"
  const val ONBOARDING_ROUTE = "onboarding"
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