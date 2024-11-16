package com.akcay.justwatch.screens.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.BottomNavSections
import com.akcay.justwatch.JustWatchNavController
import com.akcay.justwatch.addHomeGraph
import com.akcay.justwatch.internal.component.JWBottomNavBar
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.rememberJustWatchNavController
import com.akcay.justwatch.ui.theme.JustWatchTheme

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

  val showBottomBar =
    justWatchNavController.navController.currentBackStackEntryAsState().value?.destination?.route in screens.map {
      it.route
    }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      if (showBottomBar) {
        JWBottomNavBar(
          modifier = Modifier,
          navController = justWatchNavController.navController,
          selectedItem = {
            justWatchNavController.clearAndNavigate(it.route)
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