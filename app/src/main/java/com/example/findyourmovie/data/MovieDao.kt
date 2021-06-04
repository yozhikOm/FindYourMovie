package com.example.findyourmovie.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: Movie)

//    @Delete()
//    fun deleteMovie(movieID: Int)

    @Query("SELECT * from Movies")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * from Movies WHERE isFavorite=1")
    fun getFavorites(): LiveData<List<Movie>>

    @Query("SELECT * from Movies WHERE id=:id")
    fun getMovie(id: Int): LiveData<List<Movie>>

    @Query("DELETE FROM Movies")
    fun deleteAll()
}