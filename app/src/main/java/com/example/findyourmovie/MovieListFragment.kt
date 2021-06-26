package com.example.findyourmovie

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieListFragment: Fragment() {
    companion object {
        const val TAG = "MovieListFragment"
        private const val ITEMS = "ITEMS"

        fun newInstance(items: MutableList<MovieItem>): MovieListFragment {
            val args = Bundle()
            args.putParcelableArrayList(ITEMS, ArrayList(items))

            val fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items: MutableList<MovieItem> = arguments?.getParcelableArrayList<MovieItem>(ITEMS)?.toMutableList()!!
        val listener: OnMovieClickListener = object: OnMovieClickListener {
            override fun onDetailsClick(movieItem: MovieItem, position: Int) {
                movieItem.isVisited = true
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                (activity as? OnDetailsClickListener)?.onDetailsClick(movieItem, position)
            }

            override fun onFavoriteClick(movieItem: MovieItem, position: Int) {
                movieItem.isFavorite = !movieItem.isFavorite
                view.findViewById<RecyclerView>(R.id.recyclerView).adapter?.notifyItemChanged(position)
                Toast.makeText(requireContext(), "Фильм '${movieItem.title }' добавлен в избранное", Toast.LENGTH_SHORT).show()
            }
        }

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView);
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) gridLayoutManager.spanCount else 1
            }
        }
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycler.layoutManager = linearLayoutManager
        } else {
            recycler.layoutManager = gridLayoutManager
        }

        recycler.adapter = MovieAdapter(LayoutInflater.from(requireContext()), items, listener)

        val itemDecoration = CustomItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(requireContext(), R.drawable.line_4dp_grey)!!)
        recycler.addItemDecoration(itemDecoration)
    }

}