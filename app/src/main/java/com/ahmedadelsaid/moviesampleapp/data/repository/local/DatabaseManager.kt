package com.ahmedadelsaid.moviesampleapp.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager? {
            if (INSTANCE == null) {
                synchronized(DatabaseManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                                Room.databaseBuilder(context.applicationContext, DatabaseManager::class.java, "movie.db")
                                        .build()
                    }
                }
            }
            return INSTANCE
        }

    }
}