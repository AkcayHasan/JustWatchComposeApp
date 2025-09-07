package com.akcay.justwatch.internal.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.akcay.justwatch.screens.forgotpassword.ForgotPasswordScreen
import com.akcay.justwatch.screens.login.LoginScreen
import com.akcay.justwatch.screens.register.RegisterScreen
import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    object Login
    
    @Serializable
    object ForgotPassword
}

fun NavGraphBuilder.loginGraph(
    navController: NavHostController,
) {
    navigation<AppDestination.Login>(startDestination = Destinations.Login) {
        composable<Destinations.Login> {
            LoginScreen(
                navigateMovies = {
                    navController.navigate(MainDestination.Movies) {
                        popUpTo(AppDestination.Login) {
                            inclusive = true
                        }
                    }
                },
                navigateForgotPassword = {
                    navController.navigate(Destinations.ForgotPassword)
                },
            )
        }
        composable<AppDestination.Register> {
            RegisterScreen(
                navigateHome = {

                },
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable<Destinations.ForgotPassword> {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
