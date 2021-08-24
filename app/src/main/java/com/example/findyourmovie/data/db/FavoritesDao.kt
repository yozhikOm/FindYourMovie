package com.example.findyourmovie.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.findyourmovie.data.FavMovieDB

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: FavMovieDB)

    @Query("DELETE FROM Favorites WHERE id = :id")
    fun deleteMovie(id: Int)
    //@Delete
    //fun deleteMovie(movie: FavMovieDB)

    @Query("SELECT * from Favorites WHERE id=:id")
    fun getMovie(id: Int): LiveData<FavMovieDB>

    @Query("SELECT * from Favorites")
    fun getAll(): LiveData<List<FavMovieDB>>

    @Query("DELETE FROM Favorites")
    fun deleteAll()

    @Query("UPDATE Favorites SET isVisited=:isVisited WHERE id=:id")
    fun setVisited(id: Int, isVisited: Boolean)
}