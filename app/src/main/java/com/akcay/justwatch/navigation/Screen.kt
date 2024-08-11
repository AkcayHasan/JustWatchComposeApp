package com.akcay.justwatch.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class AllScreens {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    MOVIE_DETAIL,
    POPULAR_MOVIES,
    UPCOMING_MOVIES,
    PROFILE
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

    object Profile : Screen(
        route = AllScreens.PROFILE.name
    ) {
        fun createRoute() = AllScreens.PROFILE.name
    }

    object Login : Screen(
        route = AllScreens.LOGIN.name
    ) {
        fun createRoute() = AllScreens.LOGIN.name
    }

    object Register : Screen(
        route = AllScreens.REGISTER.name
    ) {
        fun createRoute() = AllScreens.REGISTER.name
    }

    object ForgotPassword : Screen(
        route = AllScreens.FORGOT_PASSWORD.name
    ) {
        fun createRoute() = AllScreens.FORGOT_PASSWORD.name
    }

}
