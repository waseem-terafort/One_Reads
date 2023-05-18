package com.terafort.onereads.repositery

import android.app.Application
import androidx.lifecycle.LiveData
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.OneReadClassData
import com.terafort.onereads.data.OneReadsDao

class FavoriteRepositery(application: Application) {

    private val movieDao: OneReadsDao

    init {
        val db = OneReadClassData.getDatabase(application)
        movieDao = db.favoriteDao()
    }

    fun getAllMovies() = movieDao.getAllMovies()

    fun getAllBookmark() = movieDao.getFavourite()

    suspend fun insertAllMovie(result: List<FavoriteData>) = movieDao.insertAllMovie(result)

    suspend fun updateBookmark(id: Int) {
        val test: Boolean = movieDao.updateBookmark(id)
        if (test) movieDao.isNotSelected(id)
        else movieDao.favorite(id)
    }
}