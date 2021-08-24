package com.example.findyourmovie.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val details: String,
    val posterPath: String?,
    var isFavorite: Boolean,
    var isVisited: Boolean
) :
    Parcelable {
}