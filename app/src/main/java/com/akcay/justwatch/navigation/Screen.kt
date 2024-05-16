package com.akcay.justwatch.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class AllScreens {
    ON_BOARDING,
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
            type = NavType.LongType
        })
    ) {

        fun createRoute(movieId: Long) = "${AllScreens.MOVIE_DETAIL.name}/$movieId"
    }

    object OnBoarding : Screen(
        route = AllScreens.ON_BOARDING.name
    ) {
        fun createRoute() = AllScreens.ON_BOARDING.name
    }

    object PopularMovies : Screen(
        route = AllScreens.POPULAR_MOVIES.name
    ) {
        fun createRoute() = AllScreens.POPULAR_MOVIES.name
    }

    object UpcomingMovies : Screen(
        route = AllScreens.UPCOMING_MOVIES.name
    ) {
        fun createRoute() = AllScreens.UPCOMING_MOVIES.name
    }

    object Settings : Screen(
        route = AllScreens.SETTINGS.name
    ) {
        fun createRoute() = AllScreens.SETTINGS.name
    }

}
