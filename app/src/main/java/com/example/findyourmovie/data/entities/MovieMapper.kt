package com.example.findyourmovie.data

import android.content.res.Resources

interface Mapper<M, DBM, NWM, FavDBM> {
    fun transformFromModelToDBModel(data: M): DBM
    fun transformFromDBModelToModel(data: DBM): M
    fun transformFromModelToNetworkModel(data: M): NWM
    fun transformFromNetworkModelToModel(data: NWM): M
    fun transformFromNetworkModelToDBModel(data: NWM): DBM

    fun transformFromModelToFavDBModel(data: M): FavDBM
    fun transformFromFavDBModelToModel(data: FavDBM): M

}

class MovieMapper : Mapper<Movie, MovieDB, MovieNetwork, FavMovieDB> {
    override fun transformFromModelToDBModel(src: Movie) = MovieDB(
        src.id,
        src.title,
        src.details,
        src.posterPath,
        src.isFavorite,
        src.isVisited
    )

    override fun transformFromDBModelToModel(src: MovieDB) = Movie(
        src.id,
        src.title,
        src.details,
        src.posterPath,
        src.isFavorite,
        src.isVisited
    )

    override fun transformFromModelToNetworkModel(src: Movie) = MovieNetwork(
        id = src.id,
        title = src.title,
        overview = src.details,
        posterPath = src.title
    )

    override fun transformFromNetworkModelToModel(src: MovieNetwork) = Movie(
        id = src.id,
        title = src.title,
        details = src.overview,
        posterPath = src.posterPath,
        isFavorite = false,
        isVisited = false
    )

    override fun transformFromNetworkModelToDBModel(src: MovieNetwork) = MovieDB(
        id = src.id,
        title = src.title,
        details = src.overview,
        posterPath = src.posterPath,
        isFavorite = false,
        isVisited = false
    )

    override fun transformFromModelToFavDBModel(src: Movie) = FavMovieDB(
        src.id,
        src.title,
        src.details,
        src.posterPath,
        src.isVisited
    )

    override fun transformFromFavDBModelToModel(src: FavMovieDB) = Movie(
        src.id,
        src.title,
        src.details,
        src.posterPath,
        false,
        src.isVisited
    )
}