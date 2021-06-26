package com.example.findyourmovie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val layoutInflater: LayoutInflater,
                   private val items: MutableList<MovieItem>,
                   private val clickListener: OnMovieClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "MovieAdapter"
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder $viewType")

        // xml -> View
        return if (viewType == VIEW_TYPE_HEADER) {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_header, parent, false)
            HeaderVH(view)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_movie, parent, false)
            MovieVH(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder $position")

        if (holder is HeaderVH) {
            holder.bind()
        }

        if (holder is MovieVH) {
            val item = items[position - 1]
            holder.bind(item)
            holder.itemView.findViewById<ImageView>(R.id.favoriteImg).setOnClickListener {
                clickListener.onFavoriteClick(item, position)
            }
            holder.itemView.findViewById<View>(R.id.detailsBtn).setOnClickListener {
                clickListener.onDetailsClick(item, position)
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1 // +1 for header

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER
        else VIEW_TYPE_ITEM
    }

}
