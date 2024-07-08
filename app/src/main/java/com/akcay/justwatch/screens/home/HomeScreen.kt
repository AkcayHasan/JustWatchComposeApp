package com.akcay.justwatch.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.R
import com.akcay.justwatch.component.JWBottomNavBar
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.screens.popular.PopularMoviesScreen
import com.akcay.justwatch.screens.profile.ProfileScreen
import com.akcay.justwatch.screens.settings.SettingsScreen
import com.akcay.justwatch.screens.upcoming.UpcomingMoviesScreen

fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    onMovieSelected: (Long) -> Unit
) {
    composable(BottomNavSections.POPULAR_MOVIES.route) {
        PopularMoviesScreen(onCardClick = onMovieSelected)
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screens = listOf(
        BottomNavSections.POPULAR_MOVIES,
        BottomNavSections.UPCOMING_MOVIES,
        BottomNavSections.PROFILE
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            //JustWatchNavHost(navController, viewModel.storeManager)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchBarPreview() {
    Scaffold {
        it
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            placeholder = {
                Text(text = "Search", color = Color.DarkGray, fontWeight = FontWeight.SemiBold)
            },
            shape = RoundedCornerShape(20.dp),
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.DarkGray
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}