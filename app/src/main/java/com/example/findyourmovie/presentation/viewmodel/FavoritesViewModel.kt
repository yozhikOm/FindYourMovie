package com.example.findyourmovie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.findyourmovie.data.*
import com.example.findyourmovie.data.db.FavoritesDatabase
import com.example.findyourmovie.data.repositories.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritesRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val favoriteMovies: LiveData<List<FavMovieDB>>

    init {
        val favoritesDao = FavoritesDatabase.getDatabase(application, viewModelScope).getFavoritesDao()
        repository = FavoritesRepository(favoritesDao)
        favoriteMovies = repository.favoriteMovies
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(movie: FavMovieDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun getMovie(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovie(id)
    }

    fun setVisited(id: Int, isVisited: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setVisited(id, isVisited)
    }
}