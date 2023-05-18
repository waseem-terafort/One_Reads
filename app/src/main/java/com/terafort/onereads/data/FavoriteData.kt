package com.terafort.onereads.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity(tableName = "Favorite")
data class FavoriteData(@PrimaryKey
                        val id: Int,
                        var documentimage:String,
                         var documentname:String,
                         var documentfiles: String,
                        var documentsdate:String,
                        var documentstime:String,
                         var favorite: Boolean=false,
                          var menuoption:String
                        ): Parcelable

