package com.terafort.onereads.data.filesdata

import android.net.Uri

data class FileModel(val name: String,
                     val nicknamefiles:String,
                     val datefiles:String,
                     val timefiles:String,
                     val imagefiles:Int,
                     var favorite: Boolean=false,
                     var menuoption:String,
                     val type: FileType,
                     val uri: Uri)
