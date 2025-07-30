package com.akcay.justwatch.internal.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Register : AppDestination

    @Serializable
    data object Login : AppDestination

    @Serializable
    data object ForgotPassword : AppDestination

    @Serializable
    data object OnBoarding : AppDestination
}
