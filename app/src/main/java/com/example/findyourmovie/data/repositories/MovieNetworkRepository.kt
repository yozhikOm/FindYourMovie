package com.example.findyourmovie.data.repositories

import com.example.findyourmovie.data.MovieNetwork
import java.util.ArrayList

class MovieNetworkRepository {
    private val cachedMovies = ArrayList<MovieNetwork>()
    private val fakeMovies = ArrayList<MovieNetwork>()

    val cachedOrFakeMovies: List<MovieNetwork>
        get() = if (cachedMovies.size > 0)
            cachedMovies
        else
            fakeMovies

    init {
        fakeMovies.add(MovieNetwork(1,"mock movie 1", null, ""))
        fakeMovies.add(MovieNetwork(2,"mock movie 2", null, ""))
        fakeMovies.add(MovieNetwork(3,"mock movie 3", null, ""))
        fakeMovies.add(MovieNetwork(4,"mock movie 4", null, ""))
    }

    fun addToCache(repos: List<MovieNetwork>) {
        this.cachedMovies.addAll(repos)
    }
}
