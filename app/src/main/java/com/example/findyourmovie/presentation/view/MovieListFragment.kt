package com.example.findyourmovie.presentation.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
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
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment: Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()
    private val favViewModel: FavoritesViewModel by activityViewModels()

    companion object {
        const val TAG = "MovieListFragment"
        const val QUERY_PAGE_SIZE = 20
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

        var isDBEmpty: Boolean = true

        tryToLoadBtn.setOnClickListener{
            getMoreData()
        }

        viewModel.getMoviesFromDB().observe(viewLifecycleOwner, Observer { movies ->
            //Показываем прогрессбар при первом запуске программы, когда БД еще пуста
            if(movies.isEmpty()) {
                showProgressBar()
                isDBEmpty = true
            }
            else {
                hideProgressBar()
                tryToLoadBtn.visibility = View.INVISIBLE
                isDBEmpty = false
            }
            adapter.setMovies(movies)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer<String> { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            hideProgressBar()

            //Показываем кнопку "Try to load", при первом запуске программы с отключенным инетом
            if(isDBEmpty){
                tryToLoadBtn.visibility = View.VISIBLE
            }
        })

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

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun getMoreData() {
        showProgressBar()
        viewModel.getMoviesFromServer()
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                getMoreData()
                isScrolling = false
            } else {
               recyclerView.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}