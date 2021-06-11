package com.example.findyourmovie.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: MovieDB)

//    @Delete()
//    fun deleteMovie(movieID: Int)

    @Query("SELECT * from Movies")
    fun getAll(): LiveData<List<MovieDB>>

    @Query("SELECT * from Movies WHERE isFavorite=1")
    fun getFavorites(): LiveData<List<MovieDB>>

    @Query("SELECT * from Movies WHERE id=:id")
    fun getMovie(id: Int): LiveData<MovieDB>

    //TODO Как делать SET в таком случае? ругается на точку в =:movie.isFavorite
    //@Query("UPDATE Movies SET [isFavorite]=:movie.isFavorite WHERE id=:movie.id")
    //fun updateMovie(movie: MovieDB)

    @Query("UPDATE Movies SET isFavorite=:isFavorite WHERE id=:id")
    fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE Movies SET isVisited=:isVisited WHERE id=:id")
    fun setVisited(id: Int, isVisited: Boolean)

    @Query("DELETE FROM Movies")
    fun deleteAll()

}