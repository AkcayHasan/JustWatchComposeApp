package com.akcay.justwatch.internal.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.akcay.justwatch.JustWatchNavController
import com.akcay.justwatch.screens.login.LoginScreen
import com.akcay.justwatch.screens.register.RegisterScreen
import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    object Login
}

fun NavGraphBuilder.loginGraph(
    navController: JustWatchNavController,
) {
    navigation<AppDestination.Login>(startDestination = Destinations.Login) {
        composable<Destinations.Login> {
            LoginScreen(
                navigateMovies = {
                    navController.navigate(MainDestination.Movies)
                },
                navigateForgotPassword = {

                },
            )
        }
        composable<AppDestination.Register> {
            RegisterScreen(
                navigateHome = {

                },
                navigateBack = {
                    navController.popUp()
                },
            )
        }
        composable<AppDestination.ForgotPassword> {

        }
    }
}
