package com.example.findyourmovie.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(val id: Int, val title: String, val details: String, val cover: Int, var isFavorite: Boolean, var isVisited: Boolean):
    Parcelable {
}

@Parcelize
@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val details: String,
    val cover: Int,
    var isFavorite: Boolean,
    var isVisited: Boolean
): Parcelable {}
