package com.example.findyourmovie.data

import android.content.res.Resources

interface Mapper<M, DBM, NWM> {
    fun transformFromModelToDBModel(data: M): DBM
    fun transformFromDBModelToModel(data: DBM): M
    fun transformFromModelToNetworkModel(data: M): NWM
    fun transformFromNetworkModelToModel(data: NWM): M
}

class MovieMapper : Mapper<Movie, MovieDB, MovieNetwork> {
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
        id = 1,
        title = src.title,
        details = src.overview,
        posterPath = src.posterPath,//Resources.ID_NULL,
        isFavorite = false,
        isVisited = false
    )
}