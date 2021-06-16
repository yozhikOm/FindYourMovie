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

data class PopularMovies(
    val results: List<MovieNetwork>
)

//data class Result(
//    val id: Int,
//    val overview: String,
//    val poster_path: String,
//    val release_date: String,
//    val title: String,
//    val vote_average: Double,
//    val vote_count: Int
//)