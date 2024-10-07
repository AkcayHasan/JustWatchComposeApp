package com.akcay.justwatch.internal.di

import com.akcay.justwatch.firebase.impl.AccountRepositoryImpl
import com.akcay.justwatch.firebase.impl.LogRepositoryImpl
import com.akcay.justwatch.firebase.service.AccountRepository
import com.akcay.justwatch.firebase.service.LogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds abstract fun provideLogService(impl: LogRepositoryImpl): LogRepository

    @Binds abstract fun provideAccountService(impl: AccountRepositoryImpl): AccountRepository
}