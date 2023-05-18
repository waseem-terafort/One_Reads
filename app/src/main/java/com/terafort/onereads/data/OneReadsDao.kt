package com.terafort.onereads.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OneReadsDao {
    @Query("select * from Favorite")
    fun getAllMovies(): LiveData<List<FavoriteData>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovie(result: List<FavoriteData>)
    // Bookmark
    @Query("select * from Favorite where favorite = 1")
    fun getFavourite(): LiveData<List<FavoriteData>>

    @Query("select favorite from Favorite where id = :id")
    suspend fun updateBookmark(id: Int): Boolean

    @Query("update Favorite set favorite = 1 where id = :id  ")
    suspend fun favorite(id: Int)

    @Query("update Favorite set favorite = 0 where id = :id")
    suspend fun isNotSelected(id: Int)

}