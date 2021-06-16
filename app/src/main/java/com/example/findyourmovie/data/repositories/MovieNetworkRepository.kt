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
        fakeMovies.add(MovieNetwork(1,"mock repo 1", "poster1", ""))
        fakeMovies.add(MovieNetwork(2,"mock repo 2", "poster2", ""))
        fakeMovies.add(MovieNetwork(3,"mock repo 3", "poster3", ""))
        fakeMovies.add(MovieNetwork(4,"mock repo 4", "poster4", ""))
    }

    fun addToCache(repos: List<MovieNetwork>) {
        this.cachedMovies.addAll(repos)
    }
}
