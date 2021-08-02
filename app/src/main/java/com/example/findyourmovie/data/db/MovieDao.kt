package com.example.findyourmovie.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.findyourmovie.data.MovieDB

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: MovieDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: ArrayList<MovieDB>)

    @Query("SELECT * from Movies")
    fun getAll(): LiveData<List<MovieDB>>

    @Query("SELECT * from Movies WHERE isFavorite=1")
    fun getFavorites(): LiveData<List<MovieDB>>

    @Query("SELECT * from Movies WHERE id=:id")
    fun getMovie(id: Int): LiveData<MovieDB>

    @Query("UPDATE Movies SET isFavorite=:isFavorite WHERE id=:id")
    fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE Movies SET isVisited=:isVisited WHERE id=:id")
    fun setVisited(id: Int, isVisited: Boolean)

    @Query("DELETE FROM Movies")
    fun deleteAll()

}