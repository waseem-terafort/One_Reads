package com.terafort.onereads.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteData::class],version = 1)
abstract class OneReadClassData : RoomDatabase(){

    abstract fun restaurantDao():OneReadsDao

}