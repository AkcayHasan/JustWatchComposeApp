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
    BottomNavSections.MOVIES,
    BottomNavSections.SEARCH,
    BottomNavSections.FAVOURITE
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
        startDestination = Screen.Movies.route,
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
  @param: StringRes val titleResId: Int,
  val selectedDrawableResId: Int,
  val notSelectedDrawableResId: Int,
  val route: String
) {
  MOVIES(
    titleResId = R.string.movies_title,
    selectedDrawableResId = R.drawable.ic_home_solid,
    notSelectedDrawableResId = R.drawable.ic_home,
    route = Screen.Movies.route
  ),
  SEARCH(
    titleResId = R.string.search_title,
    selectedDrawableResId = R.drawable.ic_search_solid,
    notSelectedDrawableResId = R.drawable.ic_search,
    route = Screen.Search.route
  ),
  FAVOURITE(
    titleResId = R.string.favourite_title,
    selectedDrawableResId = R.drawable.ic_favourite_solid,
    notSelectedDrawableResId = R.drawable.ic_favourite,
    route = Screen.Favourite.route
  )
}