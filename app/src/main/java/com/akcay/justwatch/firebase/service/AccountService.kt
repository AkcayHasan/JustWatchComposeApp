package com.akcay.justwatch.firebase.service

import com.akcay.justwatch.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)

    suspend fun register(email: String, password: String)
}