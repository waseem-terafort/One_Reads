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
            HomeData(R.drawable.all_files, "All Files", "Subtitle 1"),
            HomeData(R.drawable.pdf, "PDF", "Subtitle 2"),
            HomeData(R.drawable.excel, "Exel", "Subtitle 3"),
            HomeData(R.drawable.doc, "Doc", "Subtitle 1"),
            HomeData(R.drawable.ppt, "PPT", "Subtitle 2"),
            HomeData(R.drawable.text, "TXT", "Subtitle 3")

        )
        _myDataList.value = dataList

    }
}

