package com.example.findyourmovie

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FavoritesListFragment(val items: MutableList<MovieItem>) : Fragment() {
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
        val listener: MovieListFragment.OnMovieClickListener = object:
            MovieListFragment.OnMovieClickListener {
            override fun onDetailsClick(movieItem: MovieItem, position: Int) {
                movieItem.isVisited = true
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                (activity as? MovieListFragment.OnMovieClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: MovieItem, position:Int) {
                //(activity as? MovieListFragment.OnMovieClickListener)?.onFavoriteClick(movieItem, position)
                items.remove(movieItem)
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemRemoved(position)
            }
        }

        view.findViewById<RecyclerView>(R.id.recyclerView)
            .adapter = MovieAdapter(LayoutInflater.from(requireContext()), items, listener)

        //TODO как поменять сердечко на корзину??
        view.findViewById<ImageView>(R.id.favoriteImg)?.setImageResource(R.drawable.ic_delete_red_24dp)
    }


//    interface OnMovieClickListener {
//        fun onMovieClick(movieItem: MovieItem)
//        fun onDetailClick(movieItem: MovieItem)
//    }





}