package com.akcay.justwatch.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class AllScreens {
    MOVIE_DETAIL,
    POPULAR_MOVIES,
    UPCOMING_MOVIES,
    SETTINGS
}

sealed class Screen(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {

    object MovieDetail : Screen(
        route = "${AllScreens.MOVIE_DETAIL.name}/{movieId}",
        navArgument = listOf(navArgument("movieId") {
            type = NavType.StringType
        })
    ) {

        fun createRoute(movieId: Int) = "${AllScreens.MOVIE_DETAIL.name}/$movieId"
    }

    object PopularMovies : Screen(
        route = AllScreens.POPULAR_MOVIES.name
    ) {
        fun createRoute() = "popularMovies"
    }

    object UpcomingMovies : Screen(
        route = AllScreens.UPCOMING_MOVIES.name
    ) {
        fun createRoute() = "upcomingMovies"
    }

    object Settings : Screen(
        route = AllScreens.SETTINGS.name
    ) {
        fun createRoute() = "settings"
    }

}
