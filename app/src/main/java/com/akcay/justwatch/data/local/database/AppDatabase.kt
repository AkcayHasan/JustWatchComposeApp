package com.akcay.justwatch.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.akcay.justwatch.data.local.dao.FavoriteMovieDao
import com.akcay.justwatch.data.local.entity.FavoriteMovie

@Database(
    entities = [FavoriteMovie::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "justwatch_database_new"
                )
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
