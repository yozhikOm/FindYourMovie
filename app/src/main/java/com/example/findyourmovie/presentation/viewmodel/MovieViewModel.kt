package com.example.findyourmovie.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.findyourmovie.App
import com.example.findyourmovie.data.*
import com.example.findyourmovie.data.db.MoviesDatabase
import com.example.findyourmovie.data.network.MoviesHubInteractor
import com.example.findyourmovie.data.repositories.MovieRepository
import com.example.findyourmovie.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val TAG = "MovieViewModel"
    }

    private val moviesHubInteractor = App.instance.moviesHubInteractor
    private val repository: MovieRepository = moviesHubInteractor.movieRepository

    var page: Int = 1

    private val mError: MutableLiveData<String> = SingleLiveEvent()

    val error: LiveData<String>
        get() = mError

    init {
        getMoviesFromDB()
        getMoviesFromServer()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(movie: MovieDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }

    fun getMovie(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovie(id)
    }

    fun getMoviesFromDB(): LiveData<List<Movie>> {
        val mapper = MovieMapper()
        val dbMovies: LiveData<List<MovieDB>> = repository.allMovies

        return Transformations.distinctUntilChanged(
                Transformations.map(dbMovies) { movies -> movies.map {
                    mapper.transformFromDBModelToModel(it)
                }}
            )
    }

    fun getMoviesFromServer() = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "Page=${page}")
        moviesHubInteractor.getMovies(page, object : MoviesHubInteractor.GetMoviesCallback {
            override fun onSuccess(movies: List<MovieDB>) {
                page++
            }

            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    fun setFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setFavorite(id, isFavorite)
    }

    fun setVisited(id: Int, isVisited: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setVisited(id, isVisited)
    }
}
