package com.akcay.justwatch.internal.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Login : AppDestination

    @Serializable
    data object OnBoarding : AppDestination
    
    @Serializable
    data object Main : AppDestination
}
