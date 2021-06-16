package com.example.findyourmovie.data.network

import android.content.Context
import com.example.findyourmovie.data.MovieNetwork
import com.example.findyourmovie.data.PopularMovies
import com.example.findyourmovie.data.repositories.MovieNetworkRepository
import com.example.findyourmovie.utils.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesHubInteractor(
    private val context: Context,
    private val moviesHubService: MoviesHubAPI,
    private val movieNetworkRepository: MovieNetworkRepository
) {
    fun getMovies(page: Int = 1, callback: GetMoviesCallback) {
        moviesHubService.getMovies(Helper.getMetaData(context, "api_key")!!, page)
            .enqueue(object : Callback<PopularMovies> {
                override fun onResponse(
                    call: Call<PopularMovies>,
                    response: Response<PopularMovies>
                ) {
                    if (response.isSuccessful) {
                        movieNetworkRepository.addToCache(response.body()!!.results)

                        callback.onSuccess(movieNetworkRepository.cachedOrFakeMovies)
                    } else {
                        callback.onError("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                    callback.onError("Network error probably...")
                }
            })
    }

    interface GetMoviesCallback {
        fun onSuccess(repos: List<MovieNetwork>)
        fun onError(error: String)
    }
}
