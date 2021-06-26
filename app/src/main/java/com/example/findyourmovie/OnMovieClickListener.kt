package com.example.findyourmovie

interface OnMovieClickListener {
    fun onDetailsClick(movieItem: MovieItem, position: Int)
    fun onFavoriteClick(movieItem: MovieItem, position: Int)
}

interface  OnDetailsClickListener {
    fun onDetailsClick(movieItem: MovieItem, position: Int)
}
