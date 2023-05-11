package com.terafort.onereads.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface OneReadsDao {
    @Insert
    fun insertRestaurant(favoriteData: FavoriteData)

    @Delete
    fun deleteRestaurant(favoriteData: FavoriteData)

    @Query("SELECT * FROM Favorite")
    fun getAllRestaurants():List<FavoriteData>

    @Query("SELECT * FROM Favorite WHERE restaurant_id = :restaurantId")
    fun  getRestaurantById(restaurantId:String):FavoriteData

}