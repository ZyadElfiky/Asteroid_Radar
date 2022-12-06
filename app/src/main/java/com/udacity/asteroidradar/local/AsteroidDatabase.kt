package com.udacity.asteroidradar.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.local.entities.Asteroid

@Database(entities = [Asteroid::class], version = 3, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var instance: AsteroidDatabase?=null

        fun getDatabaseInstance(context: Context): AsteroidDatabase {
            return instance ?: synchronized(Any()) {
                instance ?: buildDatabase(context).also { instance=it }
            }

        }

        private fun buildDatabase(context: Context): AsteroidDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
        }
    }
}
