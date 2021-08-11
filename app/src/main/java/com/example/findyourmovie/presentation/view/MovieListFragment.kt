package com.example.findyourmovie.presentation.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourmovie.*
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieMapper
import com.example.findyourmovie.presentation.viewmodel.FavoritesViewModel
import com.example.findyourmovie.presentation.viewmodel.MovieViewModel

class MovieListFragment: Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()
    private val favViewModel: FavoritesViewModel by activityViewModels()

    companion object {
        const val TAG = "MovieListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapper = MovieMapper()
        val listener: OnMovieClickListener = object: OnMovieClickListener {
            override fun onDetailsClick(movieItem: Movie, position: Int) {
                viewModel.setVisited(movieItem.id, true)

                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: Movie, position: Int) {
                val newValue: Boolean = !movieItem.isFavorite

                var notificationText = if(newValue){
                    favViewModel.insert(mapper.transformFromModelToFavDBModel(movieItem))
                    "Фильм '${movieItem.title }' добавлен в избранное"
                } else{
                    favViewModel.delete(movieItem.id)
                    "Фильм '${movieItem.title }' удален из избранного"
                }

                viewModel.setFavorite(movieItem.id, newValue)
                Toast.makeText(requireContext(), notificationText, Toast.LENGTH_SHORT).show()
            }
        }

        val adapter = MovieAdapter(LayoutInflater.from(requireContext()), listener)

        initRecycler(adapter)

        viewModel.getMoviesFromDB().observe(viewLifecycleOwner, Observer { movies ->
            adapter.setMovies(movies)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer<String> { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

    }

    private fun refresh() {
        viewModel.getMoviesFromServer()
    }

    private fun initRecycler(adapter: MovieAdapter) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) gridLayoutManager.spanCount else 1
            }
        }
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.layoutManager = gridLayoutManager
        }

        val itemDecoration = CustomItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(requireContext(), R.drawable.line_4dp_grey)!!)
        recyclerView.addItemDecoration(itemDecoration)
    }
}