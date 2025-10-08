package com.akcay.justwatch.di.modules

import com.akcay.justwatch.BuildConfig
import com.akcay.justwatch.data.remote.api.ApiConfig
import com.akcay.justwatch.data.remote.api.MovieService
import com.akcay.justwatch.data.remote.api.MovieServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(OkHttp) {
            expectSuccess = false
            defaultRequest {
                url {
                    if (this.host.isBlank()) {
                        takeFrom(BuildConfig.BASE_URL)
                    }
                }
                header(HttpHeaders.Authorization, "Bearer ${ApiConfig.BEARER_TOKEN}")
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 30000
                requestTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }

            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }

    single<MovieService> { MovieServiceImpl(get()) }
}
