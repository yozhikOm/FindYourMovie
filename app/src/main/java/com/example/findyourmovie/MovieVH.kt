package com.example.findyourmovie

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val header = itemView.findViewById<TextView>(R.id.header)

    fun bind() {
        if (itemView.context as Activity is MainActivity) {
            header.setText(R.string.movies)
        } else if (itemView.context as Activity is FavoritesActivity) {
            header.setText(R.string.favorites)
        }
    }
}

class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.movieTitle)
    private val favIcon = itemView.findViewById<ImageView>(R.id.favoriteImg)
    private val details = itemView.findViewById<TextView>(R.id.details)
    private val cover = itemView.findViewById<ImageView>(R.id.cover)

    fun bind(movie: MovieItem) {
        if (itemView.context as Activity is MainActivity) {
            if (movie.isFavorite) {
                favIcon.setImageResource(R.drawable.ic_heart_red_24dp)
            } else {
                favIcon.setImageResource(R.drawable.ic_heart_grey_24dp)
            }
        }
        else if (itemView.context as Activity is FavoritesActivity) {
            favIcon.setImageResource(R.drawable.ic_delete_red_24dp)
        }

        if (movie.isVisited) {
            title.setTextColor(Color.LTGRAY)
        } else {
            title.setTextColor(Color.BLACK)
        }

        title.text = movie.title
        details.text = movie.details
        cover.setImageResource(movie.cover)
    }
}
