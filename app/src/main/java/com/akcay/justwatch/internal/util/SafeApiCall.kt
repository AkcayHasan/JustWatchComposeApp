package com.akcay.justwatch.internal.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T : Any> safeApiCall(
    defaultDispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): NetworkResult<T> {

    return try {
        withContext(defaultDispatcher) {
            val response = apiCall.invoke()
            val body = response.body()
            if (body != null && response.isSuccessful) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(code = response.code(), message = response.message())
            }
        }
    } catch (throwable: Throwable) {
        withContext(Dispatchers.Main) {
            when (throwable) {
                is SocketTimeoutException -> {
                    NetworkResult.Exception(throwable)
                }

                is IOException -> {
                    NetworkResult.Exception(throwable)
                }

                is HttpException -> {
                    NetworkResult.Error(throwable.code(), throwable.message)
                }

                else -> {
                    NetworkResult.Exception(throwable)
                }
            }
        }
    }

}