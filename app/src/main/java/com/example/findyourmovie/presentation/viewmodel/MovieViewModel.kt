package com.example.findyourmovie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.findyourmovie.App
import com.example.findyourmovie.data.*
import com.example.findyourmovie.data.db.MoviesDatabase
import com.example.findyourmovie.data.network.MoviesHubInteractor
import com.example.findyourmovie.data.repositories.MovieRepository
import com.example.findyourmovie.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesHubInteractor = App.instance.moviesHubInteractor
    private val repository: MovieRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMovies: LiveData<List<MovieDB>>
    val favoriteMovies: LiveData<List<MovieDB>>

    private val mMovies = MutableLiveData<List<MovieDB>>()
    private val mError: MutableLiveData<String> = SingleLiveEvent()

    val movies: LiveData<List<MovieDB>>
        get() = mMovies

    val error: LiveData<String>
        get() = mError


    init {
        val moviesDao = MoviesDatabase.getDatabase(application, viewModelScope).getMovieDao()
        repository = MovieRepository(moviesDao)
        allMovies = repository.allMovies
        favoriteMovies = repository.favoriteMovies
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

    fun getAllMovies() = viewModelScope.launch(Dispatchers.IO) {
        moviesHubInteractor.getMovies(1, object : MoviesHubInteractor.GetMoviesCallback {
            override fun onSuccess(movies: List<MovieDB>) {
                mMovies.value = movies
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
