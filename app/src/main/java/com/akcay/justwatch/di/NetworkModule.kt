package com.akcay.justwatch.di

import android.content.Context
import com.akcay.justwatch.BuildConfig
import com.akcay.justwatch.network.MovieService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONNECTION_TIMEOUT = 30L
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDI4Y2VkYWEwZjFkNWI2MmIxNWM1ZWQwZTgwNTg3OCIsInN1YiI6IjYxODIzYTkwMTEzODZjMDAyYTljZDkyMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DRVTFDZL_APN2Sj3_RXwNaMrhjyC2g_4W9zQADYC2c8"

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context = context))
            .addInterceptor {
                val request = it.request()
                val newRequest = request.newBuilder()
                    .addHeader("accept","application/json")
                    .addHeader("Authorization","Bearer $BEARER_TOKEN")
                    .build()

                it.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

}