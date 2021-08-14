package com.example.findyourmovie.data

import com.google.gson.annotations.SerializedName

data class MovieNetwork(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("overview")
    val overview: String,

    )

data class NetworkResponse(
    val results: List<MovieNetwork>,
    val page: Int
)
