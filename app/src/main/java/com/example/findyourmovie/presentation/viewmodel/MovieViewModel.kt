package com.example.findyourmovie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieRepository
import com.example.findyourmovie.data.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMovies: LiveData<List<Movie>>
    val favoriteMovies: LiveData<List<Movie>>

    init {
        val moviesDao = MovieRoomDatabase.getDatabase(application, viewModelScope).getMovieDao()
        repository = MovieRepository(moviesDao)
        allMovies = repository.allMovies
        favoriteMovies = repository.favoriteMovies
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }
}
