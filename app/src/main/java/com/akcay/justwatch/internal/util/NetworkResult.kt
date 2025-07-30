package com.akcay.justwatch.internal.util

sealed class NetworkResult<T : Any> {

    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

inline fun <T : Any, R : Any> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Error -> NetworkResult.Error(code, message)
        is NetworkResult.Exception -> NetworkResult.Exception(e)
    }
}
