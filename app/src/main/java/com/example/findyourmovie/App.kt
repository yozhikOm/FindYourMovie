package com.example.findyourmovie

import android.app.Application
import com.example.findyourmovie.data.network.MoviesHubInteractor
import com.example.findyourmovie.data.network.MoviesHubAPI
import com.example.findyourmovie.data.network.MoviesHubUpdater
import com.example.findyourmovie.data.repositories.MovieNetworkRepository
import com.example.findyourmovie.utils.Helper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    lateinit var moviesHubAPI: MoviesHubAPI
    lateinit var moviesHubUpdater: MoviesHubUpdater
    lateinit var moviesHubInteractor: MoviesHubInteractor
    var movieNetworkRepository = MovieNetworkRepository()

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initRetrofit()
        initInteractor()
    }

    private fun initRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        moviesHubAPI = Retrofit.Builder()
            .baseUrl(Helper.getMetaData(this, "api_url")!!)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MoviesHubAPI::class.java)

        moviesHubUpdater = MoviesHubUpdater(this, moviesHubAPI)
    }

    private fun initInteractor() {
        moviesHubInteractor = MoviesHubInteractor(this, moviesHubAPI, movieNetworkRepository)
    }

}