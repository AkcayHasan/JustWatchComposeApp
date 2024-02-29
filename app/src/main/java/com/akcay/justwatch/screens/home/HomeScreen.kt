package com.akcay.justwatch.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.JustWatchNavHost
import com.akcay.justwatch.R
import com.akcay.justwatch.component.JWBottomNavBar
import com.akcay.justwatch.component.ListMovieItem
import com.akcay.justwatch.data.model.MovieResponse
import com.akcay.justwatch.data.model.MovieResult
import com.akcay.justwatch.navigation.Screen

enum class JustWatchBottomNavBarItems(
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
    SETTINGS(R.string.settings_title, Icons.Default.Settings, Screen.Settings.route)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val screens = listOf(
        JustWatchBottomNavBarItems.POPULAR_MOVIES,
        JustWatchBottomNavBarItems.UPCOMING_MOVIES,
        JustWatchBottomNavBarItems.SETTINGS
    )

    val showBottomBar =
        navController.currentBackStackEntryAsState().value?.destination?.route in screens.map {
            it.route
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                JWBottomNavBar(
                    modifier = modifier,
                    navController = navController,
                    selectedItem = {
                        navController.navigate(it.route)
                    },
                    items = screens
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            JustWatchNavHost(navController)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenPreview() {
    val list = MovieResponse(
        page = null,
        movieResults = arrayListOf(
            MovieResult(),
            MovieResult(),
            MovieResult(),
        ),
        totalPages = null,
        totalResults = null
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "List") }) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxSize()
        ) {
            LazyColumn {
                list.movieResults?.let { itemsList ->
                    items(itemsList.size) { index ->
                        ListMovieItem(
                            imageUrl = "",
                            itemId = itemsList[index].id ?: 0,
                            movieName = itemsList[index].originalTitle ?: "",
                            onCardClicked = {},
                            onAddIconClicked = {}
                        )
                    }
                }
            }
        }
    }
}