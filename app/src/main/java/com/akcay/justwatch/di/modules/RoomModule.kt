package com.akcay.justwatch.di.modules

import android.content.Context
import androidx.room.Room
import com.akcay.justwatch.data.local.database.AppDatabase
import com.akcay.justwatch.data.local.dao.FavoriteMovieDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "justwatch_database"
        ).build()
    }

    single<FavoriteMovieDao> {
        get<AppDatabase>().favoriteMovieDao()
    }
}
