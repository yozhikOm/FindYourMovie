package com.example.findyourmovie.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movies")
data class MovieDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val details: String,
    val posterPath: String?,
    var isFavorite: Boolean,
    var isVisited: Boolean
)