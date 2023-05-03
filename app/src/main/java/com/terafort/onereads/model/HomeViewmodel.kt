package com.terafort.onereads.model


import android.app.Application
import android.util.Log

import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.terafort.onereads.R
import com.terafort.onereads.data.HomeData



class HomeViewmodel(application: Application) : AndroidViewModel(application) {
    private val _myDataList = MutableLiveData<List<HomeData>>()
    val myDataList: LiveData<List<HomeData>>
        get() = _myDataList

    init {
        val dataList = listOf(
            HomeData(R.drawable.pdf_file_icon_svg, "Item 1", "Subtitle 1"),
            HomeData(R.drawable.pdf_file_icon_svg, "Item 2", "Subtitle 2"),
            HomeData(R.drawable.pdf_file_icon_svg, "Item 3", "Subtitle 3")

        )
        _myDataList.value = dataList
        Log.e("homedata3", "data is showing")
    }
}

