package com.akcay.justwatch.data.remote.api

import com.akcay.justwatch.internal.util.NetworkResult
import io.ktor.http.HttpStatusCode
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <reified T : Any> makeApiCall(
    crossinline apiCall: suspend () -> HttpResponse,
): NetworkResult<T> {
    return try {
        withContext(Dispatchers.IO) {
            val response = apiCall.invoke()
            return@withContext if (response.status.isSuccess()) {
                NetworkResult.Success(response.body())
            } else {
                val errorResponse = response.bodyAsText()
                NetworkResult.Error(response.status.value, errorResponse)
            }

        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> {
                NetworkResult.Exception(throwable)
            }

            is IOException -> {
                NetworkResult.Exception(throwable)
            }

            else -> {
                NetworkResult.Exception(throwable)
            }
        }
    }
}

fun HttpStatusCode.isSuccess() = value in 200..299
