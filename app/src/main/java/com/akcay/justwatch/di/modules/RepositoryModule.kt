package com.akcay.justwatch.di.modules

import com.akcay.justwatch.data.repository.AccountRepositoryImpl
import com.akcay.justwatch.data.repository.LogRepositoryImpl
import com.akcay.justwatch.data.repository.MovieRepositoryImpl
import com.akcay.justwatch.data.local.repository.FavoriteRepositoryImpl
import com.akcay.justwatch.data.remote.datasource.MovieRemoteDataSource
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.domain.repository.FavoriteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LogRepositoryImpl) { bind<LogRepository>() }
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
    single<MovieRemoteDataSource> { MovieRemoteDataSource(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}
