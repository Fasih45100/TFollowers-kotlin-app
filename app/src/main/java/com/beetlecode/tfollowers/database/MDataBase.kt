package com.beetlecode.tfollowers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Post::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase: RoomDatabase() {

    abstract fun getMyDao(): MyDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MyDatabase::class.java, "my_tweet.db").build()
    }
}