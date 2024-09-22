package com.akcay.justwatch.internal.di

import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun provideMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository
}