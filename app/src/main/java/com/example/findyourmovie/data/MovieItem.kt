package com.example.findyourmovie.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val id: Int, val title: String, val details: String, val cover: Int, var isFavorite: Boolean, var isVisited: Boolean):
    Parcelable {
}

@Entity(tableName = "Movies")
data class MovieDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val details: String,
    val cover: Int,
    var isFavorite: Boolean,
    var isVisited: Boolean
)


