package com.terafort.onereads.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteData::class],version = 2)
abstract class OneReadClassData : RoomDatabase(){

    abstract fun favoriteDao():OneReadsDao
    companion object{
        private var instance: OneReadClassData?= null

        @Synchronized
        fun getDatabase(context: Context): OneReadClassData{
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    OneReadClassData::class.java,"movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}