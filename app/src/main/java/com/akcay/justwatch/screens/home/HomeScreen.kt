package com.akcay.justwatch.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.TextFieldColors
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.JustWatchNavHost
import com.akcay.justwatch.R
import com.akcay.justwatch.component.JWBottomNavBar
import com.akcay.justwatch.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            JustWatchNavHost(navController, viewModel.storeManager)
        }
    }

}

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