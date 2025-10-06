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
    object Register
    
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
                navigateRegister = {
                    navController.navigate(Destinations.Register)
                },
                navigateForgotPassword = {
                    navController.navigate(Destinations.ForgotPassword)
                },
            )
        }
        composable<Destinations.Register> {
            RegisterScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateMovies = {
                    navController.navigate(MainDestination.Movies) {
                        popUpTo(AppDestination.Login) {
                            inclusive = true
                        }
                    }
                }
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
