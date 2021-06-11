package com.example.findyourmovie.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourmovie.R
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieMapper
import com.example.findyourmovie.presentation.viewmodel.MovieViewModel

class FavoritesListFragment() : Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()

    companion object {
        const val TAG = "FavoritesListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listener: OnMovieClickListener = object:
            OnMovieClickListener {
            override fun onDetailsClick(movieItem: Movie, position: Int) {
                viewModel.setVisited(movieItem.id, true)

                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: Movie, position:Int) {
                viewModel.setFavorite(movieItem.id, false)

                Toast.makeText(requireContext(), "Фильм '${movieItem.title }' удален из избранного", Toast.LENGTH_SHORT).show()
            }
        }
        val adapter = MovieAdapter(LayoutInflater.from(requireContext()), listener)

        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

//        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer { movies ->
//            // Update the cached copy of the movies in the adapter.
//            movies?.let { adapter.setMovies(it) }
//        })
        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer { movies ->
            // Update the cached copy of the movies in the adapter.

            movies?.let {
                val mapper = MovieMapper();
                val transformedMovies: MutableList<Movie> = ArrayList()
                it.forEach{ mdb ->
                    val movie: Movie = mapper.transformFromDBModelToModel(mdb)
                    transformedMovies.add(movie)
                }
                adapter.setMovies(transformedMovies)
            }
        })

    }


}