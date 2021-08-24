package com.example.findyourmovie.data.network

import com.example.findyourmovie.data.NetworkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesHubAPI {
    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int = 1
    ): Call<NetworkResponse>
}

