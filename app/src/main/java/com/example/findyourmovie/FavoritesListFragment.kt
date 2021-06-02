package com.example.findyourmovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FavoritesListFragment() : Fragment() {
    lateinit var items: MutableList<MovieItem>

    companion object {
        const val TAG = "FavoritesListFragment"
        private const val FAV_ITEMS = "FAV_ITEMS"

        fun newInstance(favoriteItems: MutableList<MovieItem>): Fragment {
            val args = Bundle()
            args.putParcelableArrayList(FAV_ITEMS, ArrayList(favoriteItems))

            val fragment = FavoritesListFragment()
            fragment.arguments = args
            return fragment
        }
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
        items = arguments?.getParcelableArrayList<MovieItem>(FAV_ITEMS)?.toMutableList()!!

        val listener: OnMovieClickListener = object:
            OnMovieClickListener {
            override fun onDetailsClick(movieItem: MovieItem, position: Int) {
                movieItem.isVisited = true
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: MovieItem, position:Int) {
                items.remove(movieItem)
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Фильм '${movieItem.title }' удален из избранного", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<RecyclerView>(R.id.recyclerView)
            .adapter = MovieAdapter(LayoutInflater.from(requireContext()), items, listener)

    }


}