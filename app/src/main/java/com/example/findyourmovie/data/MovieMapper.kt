package com.example.findyourmovie.data

interface Mapper<M, DBM> {
    fun transformFromModelToDBModel(data: M): DBM
    fun transformFromDBModelToModel(data: DBM): M
}

class MovieMapper : Mapper<Movie, MovieDB> {
    override fun transformFromModelToDBModel(src: Movie) = MovieDB(
        src.id,
        src.title,
        src.details,
        src.cover,
        src.isFavorite,
        src.isVisited
    )

    override fun transformFromDBModelToModel(src: MovieDB) = Movie(
        src.id,
        src.title,
        src.details,
        src.cover,
        src.isFavorite,
        src.isVisited
    )
}