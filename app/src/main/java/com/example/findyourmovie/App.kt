package com.example.findyourmovie

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.findyourmovie.data.db.MoviesDatabase
import com.example.findyourmovie.data.network.MoviesHubAPI
import com.example.findyourmovie.data.network.MoviesHubInteractor
import com.example.findyourmovie.data.network.MoviesHubUpdater
import com.example.findyourmovie.data.repositories.MovieRepository
import com.example.findyourmovie.utils.Helper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App: Application() {
    lateinit var moviesHubAPI: MoviesHubAPI
    lateinit var moviesHubUpdater: MoviesHubUpdater
    lateinit var moviesHubInteractor: MoviesHubInteractor
    lateinit var repository: MovieRepository
    private var INSTANCE: MoviesDatabase? = null

    companion object {
        lateinit var instance: App
            private set
    }

    fun getDatabase(context: Context): MoviesDatabase {
        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies_database.db"
            )
                // Wipes and rebuilds instead of migrating if no Migration object.
                // Migration is not part of this codelab.
                .fallbackToDestructiveMigration()
                //.addCallback(MovieRoomDatabase.Companion.MovieDatabaseCallback(scope))
                .build()
            INSTANCE = instance
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()

        instance = this

        val db: MoviesDatabase = getDatabase(this)
        val dao = db.getMovieDao()
        repository = MovieRepository(dao)

        initRetrofit()
        initInteractor()
    }

    private fun initRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .pingInterval(1, TimeUnit.SECONDS)
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
        moviesHubInteractor = MoviesHubInteractor(this, moviesHubAPI, repository)
    }

}