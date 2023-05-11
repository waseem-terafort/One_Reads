package com.terafort.onereads.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class FavoriteData(@ColumnInfo(name="restaurant_id") @PrimaryKey var restaurantId:String,
                        @ColumnInfo(name="restaurant_name") var restaurantName:String,
                        @ColumnInfo(name="restaurant_rating") var restaurentRating: String,
                        @ColumnInfo(name="restaurant_cost") var costPerPerson: Int,
                        @ColumnInfo(name="restaurant_image")  var restaurantImage:String
                        )

