package com.akcay.justwatch

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.util.ClickActions

object MainDestinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun rememberJustWatchNavController(
    navController: NavHostController = rememberNavController()
): JustWatchNavController = remember(navController) {
    JustWatchNavController(navController)
}

class JustWatchNavController(
    val navController: NavHostController
) {

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun clickActions(actions: ClickActions, args: Bundle? = null) {
        when (actions) {
            ClickActions.SKIP_ONBOARDING -> {

            }

            ClickActions.NEXT_ONBOARDING -> {

            }

            ClickActions.PREVIOUS_ONBOARDING -> {

            }

            ClickActions.GET_STARTED_ONBOARDING -> {

            }
        }
    }
}