package com.example.findyourmovie

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
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieItem
import com.example.findyourmovie.presentation.viewmodel.MovieViewModel

class FavoritesListFragment() : Fragment() {
    //lateinit var items: MutableList<MovieItem>
    private val viewModel: MovieViewModel by activityViewModels()

    companion object {
        const val TAG = "FavoritesListFragment"
        private const val FAV_ITEMS = "FAV_ITEMS"

//        fun newInstance(favoriteItems: MutableList<MovieItem>): Fragment {
//            val args = Bundle()
//            args.putParcelableArrayList(FAV_ITEMS, ArrayList(favoriteItems))
//
//            val fragment = FavoritesListFragment()
//            fragment.arguments = args
//            return fragment
//        }
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
        //items = arguments?.getParcelableArrayList<MovieItem>(FAV_ITEMS)?.toMutableList()!!

        val listener: OnMovieClickListener = object:
            OnMovieClickListener {
            override fun onDetailsClick(movieItem: Movie, position: Int) {
                movieItem.isVisited = true
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: Movie, position:Int) {
                //items.remove(movieItem)
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Фильм '${movieItem.title }' удален из избранного", Toast.LENGTH_SHORT).show()
            }
        }
        val adapter = MovieAdapter(LayoutInflater.from(requireContext()), listener)

        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer { movies ->
            // Update the cached copy of the words in the adapter.
            movies?.let { adapter.setMovies(it) }
        })

    }


}