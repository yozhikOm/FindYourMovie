package com.example.findyourmovie

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieItem
import com.example.findyourmovie.presentation.viewmodel.MovieViewModel

class MovieListFragment: Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()
    //private lateinit var viewModel: MovieViewModel

    companion object {
        const val TAG = "MovieListFragment"
        private const val ITEMS = "ITEMS"

//        fun newInstance(items: MutableList<MovieItem>): MovieListFragment {
//            val args = Bundle()
//            args.putParcelableArrayList(ITEMS, ArrayList(items))
//
//            val fragment = MovieListFragment()
//            fragment.arguments = args
//            return fragment
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val items: MutableList<MovieItem> = arguments?.getParcelableArrayList<MovieItem>(ITEMS)?.toMutableList()!!

        val listener: OnMovieClickListener = object: OnMovieClickListener {
            override fun onDetailsClick(movieItem: Movie, position: Int) {
                movieItem.isVisited = true
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: Movie, position: Int) {
                movieItem.isFavorite = !movieItem.isFavorite
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                Toast.makeText(requireContext(), "Фильм '${movieItem.title }' добавлен в избранное", Toast.LENGTH_SHORT).show()
            }
        }

        //val adapter = MovieAdapter(LayoutInflater.from(requireContext()), items, listener)
        val adapter = MovieAdapter(LayoutInflater.from(requireContext()), listener)

        initRecycler(adapter)

//        viewModel.repos.observe(viewLifecycleOwner, Observer<List<Repo>> { repos ->
//            adapter.setItems(repos)
//        })
//
//        viewModel.error.observe(viewLifecycleOwner, Observer<String> { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//        })


        // Get a new or existing ViewModel from the ViewModelProvider.
        //viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        viewModel.allMovies.observe(viewLifecycleOwner, Observer { movies ->
            // Update the cached copy of the words in the adapter.
            movies?.let { adapter.setMovies(it) }
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
    }
}