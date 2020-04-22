package com.example.dogbreed.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/* [DogBreed::class] is arrayOf() literal, same as arrayOf(DogBreed::class)
 DogsDatabase is a Singleton to avoid multiple access to the DB from various locations at the same time
  it'll be only one instance of the DB each time */
@Database(entities = [DogBreed::class], version = 1)
abstract class DogsDatabase : RoomDatabase() {
    abstract fun dogDao(): DogsDao // instantiating the DogsDao interface

    /* companion object creates static variables and methods that can be accessed outside the scope of this class */
    companion object {
        @Volatile
        private var instance: DogsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogsDatabase::class.java,
            "dogsdatabase"
        ).build()
    }
}