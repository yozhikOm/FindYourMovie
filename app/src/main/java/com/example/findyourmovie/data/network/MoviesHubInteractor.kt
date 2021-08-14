package com.example.findyourmovie.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.findyourmovie.data.*
import com.example.findyourmovie.data.repositories.MovieRepository
import com.example.findyourmovie.utils.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesHubInteractor(
    private val context: Context,
    private val moviesHubService: MoviesHubAPI,
    val movieRepository: MovieRepository
) {
    companion object {
        const val TAG = "MoviesHubInteractor"
    }

    fun getMovies(page: Int, callback: GetMoviesCallback) {
        Log.d(TAG, "Page: ${page}")
        moviesHubService.getMovies(Helper.getMetaData(context, "api_key")!!, page)
            .enqueue(object : Callback<NetworkResponse> {
                override fun onResponse(
                    call: Call<NetworkResponse>,
                    response: Response<NetworkResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Cool! got it!")
                        val mapper = MovieMapper()
                        val transformedMovies: ArrayList<MovieDB> = ArrayList()
                        response.body()!!.results.forEach { movieNW ->
                            val movie: MovieDB =
                                mapper.transformFromNetworkModelToDBModel(movieNW)
                            transformedMovies.add(movie)
                        }
                        movieRepository.insertAll(transformedMovies)
                        callback.onSuccess(transformedMovies)
                    } else {
                        callback.onError("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                    Log.d(TAG, "Found error: ${t}")
                    callback.onError("Network error probably...")
                }
            })
    }

    interface GetMoviesCallback {
        fun onSuccess(repos: List<MovieDB>)
        fun onError(error: String)
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}
