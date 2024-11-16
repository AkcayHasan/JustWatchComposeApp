package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.internal.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUserId: String
    val hasUser: Boolean
    val currentAuthUser: Flow<AuthUser?>

    suspend fun signIn(email: String, password: String): NetworkResult<AuthUser>
    suspend fun signOut(): NetworkResult<Unit>
    suspend fun register(email: String, password: String): NetworkResult<AuthUser>
    suspend fun saveUserInfo(userId: String, name: String, lastName: String): NetworkResult<Boolean>
    suspend fun getUserInfo(userId: String): NetworkResult<User>
    suspend fun createAnonymousUser(): NetworkResult<Unit>
}