package com.terafort.onereads.data

data class HomeData(
    var homeimageview: Int,
    var textViewHeader: String,
    var textViewnickname: String,
    var type: String,
    var isFavorite: Boolean = false
)