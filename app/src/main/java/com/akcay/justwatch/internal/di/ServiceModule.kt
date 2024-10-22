package com.akcay.justwatch.internal.di

import com.akcay.justwatch.data.repository.AccountRepositoryImpl
import com.akcay.justwatch.data.repository.LogRepositoryImpl
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds abstract fun provideLogService(impl: LogRepositoryImpl): LogRepository

    @Binds abstract fun provideAccountService(impl: AccountRepositoryImpl): AccountRepository
}