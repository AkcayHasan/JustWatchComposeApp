package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.internal.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUserId: String
    val hasUser: Boolean
    val currentAuthUser: Flow<AuthUser?>

    suspend fun signIn(email: String, password: String): NetworkResult<AuthUser>
    suspend fun signOut(): NetworkResult<Unit>
    suspend fun register(email: String, password: String): NetworkResult<AuthUser>
    suspend fun createAnonymousUser(): NetworkResult<Unit>
}