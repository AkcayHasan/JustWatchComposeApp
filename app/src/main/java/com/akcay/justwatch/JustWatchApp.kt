package com.akcay.justwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.internal.navigation.AppDestination
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.loginGraph
import com.akcay.justwatch.internal.navigation.mainGraph
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen

@Composable
fun JustWatchApp(
    viewModel: JustWatchViewModel = hiltViewModel(),
    startDestination: AppDestination,
) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<AppDestination.OnBoarding> {
                OnBoardingScreen(
                    onComplete = {

                    },
                )
            }
            composable<AppDestination.Main> {
                LaunchedEffect(Unit) {
                    navController.navigate(MainDestination.Movies) {
                        popUpTo(AppDestination.Main) {
                            inclusive = true
                        }
                    }
                }
            }
            loginGraph(navController = navController)
            mainGraph(navController = navController)
        }
    }
}