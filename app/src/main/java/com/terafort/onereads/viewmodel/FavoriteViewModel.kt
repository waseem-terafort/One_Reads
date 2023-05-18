package com.terafort.onereads.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.repositery.FavoriteRepositery
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(application: Application) : AndroidViewModel(application) {
    private var repository = FavoriteRepositery(application)
    private val homeViewModel = HomeViewmodel(application)
    private val movieLiveData = MutableLiveData<Boolean>()

    val filteredMovieList = Transformations.switchMap(movieLiveData) {
        if (it) repository.getAllBookmark()
        else repository.getAllMovies()
    }
    init {

        homeViewModel.myDataList.observeForever { homeDataList ->
            val favoriteDataList = homeDataList.map { homeData ->
                FavoriteData(
                    homeData.homeimageview,
                    homeData.textViewHeader,
                    homeData.textViewnickname,
                    documentsdate = "date",
                    documentstime = "time",
                    documentfiles = "file_path",
                    menuoption = "menu_option"
                )
            }
            insertAllMovies(favoriteDataList)
        }
    }

    fun getAllBookmark(movie: Boolean) {
        movieLiveData.value = movie
    }

    fun insertAllMovies(result: List<FavoriteData>) {
        viewModelScope.launch {
            repository.insertAllMovie(result)
        }
    }

    fun updateBookmark(id: Int) {
        viewModelScope.launch {
            repository.updateBookmark(id)
        }
    }
}