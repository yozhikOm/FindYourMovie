package com.example.findyourmovie.data.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.findyourmovie.data.FavMovieDB
import com.example.findyourmovie.data.db.FavoritesDao

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class FavoritesRepository(private val favoritesDao: FavoritesDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val favoriteMovies: LiveData<List<FavMovieDB>> = favoritesDao.getAll()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: FavMovieDB) {
        favoritesDao.addMovie(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        favoritesDao.deleteMovie(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMovie(id: Int) {
        favoritesDao.getMovie(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun setVisited(id: Int, isVisited: Boolean) {
        favoritesDao.setVisited(id, isVisited)
    }
}
