package com.akcay.justwatch.screens.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.akcay.justwatch.R
import com.akcay.justwatch.addHomeGraph
import com.akcay.justwatch.internal.component.JWBottomNavBar
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.rememberJustWatchNavController

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  onNavigateToMovieDetail: (Long) -> Unit
) {
  val justWatchNavController = rememberJustWatchNavController()
  val screens = listOf(
    BottomNavSections.POPULAR_MOVIES,
    BottomNavSections.UPCOMING_MOVIES,
    BottomNavSections.PROFILE
  )

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      JWBottomNavBar(
        modifier = Modifier,
        navController = justWatchNavController.navController,
        selectedItem = {
          justWatchNavController.clearAndNavigate(it.route)
        },
        items = screens
      )
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = it.calculateBottomPadding())
    ) {
      NavHost(
        navController = justWatchNavController.navController,
        startDestination = Screen.PopularMovies.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
      ) {
        addHomeGraph { id ->
          onNavigateToMovieDetail.invoke(id)
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {

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