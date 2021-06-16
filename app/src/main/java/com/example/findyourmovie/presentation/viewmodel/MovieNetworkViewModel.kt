package com.example.findyourmovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findyourmovie.App
import com.example.findyourmovie.data.MovieNetwork
import com.example.findyourmovie.data.network.MoviesHubInteractor
import com.example.findyourmovie.utils.SingleLiveEvent


class MovieNetworkViewModel : ViewModel() {

    init {
        Log.d("MovieNetworkViewModel", this.toString())
    }

    private val githubInteractor = App.instance.moviesHubInteractor

    private val mMovies = MutableLiveData<List<MovieNetwork>>()
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private val mSelectedRepoUrl = MutableLiveData<String>()

    val movies: LiveData<List<MovieNetwork>>
        get() = mMovies

    val error: LiveData<String>
        get() = mError

    val selectedRepoUrl: LiveData<String> = mSelectedRepoUrl

    fun onGetDataClick() {
        githubInteractor.getMovies(1, object : MoviesHubInteractor.GetMoviesCallback {
            override fun onSuccess(movies: List<MovieNetwork>) {
                mMovies.value = movies
            }

            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    fun onRepoSelect(repoUrl: String) {
        mSelectedRepoUrl.value = repoUrl
    }

}
