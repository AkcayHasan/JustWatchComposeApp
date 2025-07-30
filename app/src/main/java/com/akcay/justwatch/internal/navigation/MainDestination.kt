package com.akcay.justwatch.internal.navigation

import kotlinx.serialization.Serializable

sealed interface MainDestination {

    @Serializable
    data object Movies : MainDestination

    @Serializable
    data object Search : MainDestination

    @Serializable
    data object Favourite : MainDestination

    @Serializable
    data class MovieDetail(val id: Long) : MainDestination
}
