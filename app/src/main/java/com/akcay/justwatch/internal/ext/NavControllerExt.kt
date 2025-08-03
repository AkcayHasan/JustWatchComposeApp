package com.akcay.justwatch.internal.ext

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import com.akcay.justwatch.internal.navigation.MainDestination

fun NavController.navigateToTab(destination: MainDestination) {
    if (currentDestination?.hasRoute(destination::class) == true) return

    navigate(destination) {
        popUpTo(MainDestination.Movies) {
            inclusive = false
            saveState = true
        }

        launchSingleTop = true
        restoreState = destination == MainDestination.Movies
    }
}
