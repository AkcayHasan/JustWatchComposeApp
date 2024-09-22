package com.akcay.justwatch.internal.di

import com.akcay.justwatch.firebase.impl.AccountServiceImpl
import com.akcay.justwatch.firebase.impl.LogServiceImpl
import com.akcay.justwatch.firebase.service.AccountService
import com.akcay.justwatch.firebase.service.LogService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}