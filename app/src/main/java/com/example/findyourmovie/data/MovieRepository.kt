package com.example.findyourmovie.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class MovieRepository(private val movieDao: MovieDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allMovies: LiveData<List<Movie>> = movieDao.getAll()

    val favoriteMovies: LiveData<List<Movie>> = movieDao.getFavorites()
    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: Movie) {
        movieDao.addMovie(movie)
    }
}
