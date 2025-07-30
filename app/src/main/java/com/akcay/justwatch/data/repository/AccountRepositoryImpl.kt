package com.akcay.justwatch.data.repository

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AccountRepository {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentAuthUser: Flow<AuthUser>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(
                    auth.currentUser?.let {
                        AuthUser(
                            name = it.displayName,
                            email = it.email,
                            id = it.uid,
                            isAnonymous = it.isAnonymous,
                        )
                    } ?: AuthUser(),
                )
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun signIn(email: String, password: String): NetworkResult<AuthUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            NetworkResult.Success(
                AuthUser(
                    name = result.user?.displayName,
                    email = result.user?.email,
                    id = result.user?.uid,
                    isAnonymous = result.user?.isAnonymous,
                ),
            )
        } catch (exception: Exception) {
            NetworkResult.Exception(exception)
        }
    }

    override suspend fun signOut(): NetworkResult<Unit> {
        return try {
            auth.signOut()
            NetworkResult.Success(Unit)
        } catch (exception: Exception) {
            NetworkResult.Exception(exception)
        }
    }

    override suspend fun register(email: String, password: String): NetworkResult<AuthUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                NetworkResult.Success(
                    AuthUser(
                        name = result.user?.displayName,
                        email = result.user?.email,
                        id = result.user?.uid,
                        isAnonymous = result.user?.isAnonymous,
                    ),
                )
            } catch (exception: Exception) {
                NetworkResult.Exception(exception)
            }
        }
    }

    override suspend fun saveUserInfo(
        userId: String,
        name: String,
        lastName: String,
    ): NetworkResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val user = hashMapOf(
                    "firstName" to name,
                    "lastName" to lastName,
                )
                firestore.collection("users").document(userId).set(user).await()
                NetworkResult.Success(true)
            } catch (exception: Exception) {
                NetworkResult.Exception(exception)
            }
        }
    }

    override suspend fun getUserInfo(userId: String): NetworkResult<User> {
        return withContext(Dispatchers.IO) {
            try {
                val document = firestore.collection("users").document(userId).get().await()
                if (document.exists()) {
                    val firstName = document.getString("firstName") ?: ""
                    val lastName = document.getString("lastName") ?: ""
                    NetworkResult.Success(User(firstName, lastName))
                } else {
                    NetworkResult.Error(1, "")
                }
            } catch (exception: Exception) {
                NetworkResult.Exception(exception)
            }
        }
    }

    override suspend fun createAnonymousUser(): NetworkResult<Unit> {
        return try {
            auth.signInAnonymously().await()
            NetworkResult.Success(Unit)
        } catch (exception: Exception) {
            NetworkResult.Exception(exception)
        }
    }
}
