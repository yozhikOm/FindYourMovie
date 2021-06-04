package com.example.findyourmovie

import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieItem

interface OnMovieClickListener {
    fun onDetailsClick(movieItem: Movie, position: Int)
    fun onFavoriteClick(movieItem: Movie, position: Int)
}

interface  OnDetailsClickListener {
    fun onDetailsClick(movieItem: Movie, position: Int)
}
