package com.example.findyourmovie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(val title: String, val details: String, val cover: Int, var isFavorite: Boolean, var isVisited: Boolean):
    Parcelable {
}