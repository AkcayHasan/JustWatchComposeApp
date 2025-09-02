package com.akcay.justwatch

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.internal.navigation.AppDestination
import com.akcay.justwatch.internal.navigation.loginGraph
import com.akcay.justwatch.internal.navigation.mainGraph
import com.akcay.justwatch.screens.onboarding.OnBoardingScreen

@Composable
fun <T : Any> JustWatchApp(
    viewModel: JustWatchViewModel = hiltViewModel(),
    startDestination: T,
) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composable<AppDestination.OnBoarding> {
                OnBoardingScreen(
                    onComplete = {

                    },
                )
            }
            loginGraph(navController = navController)
            mainGraph(navController = navController)
        }
    }
}
