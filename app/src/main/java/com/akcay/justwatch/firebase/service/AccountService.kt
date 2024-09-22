package com.akcay.justwatch.firebase.service

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)

    suspend fun register(email: String, password: String)

    suspend fun createAnonymousUser()
}