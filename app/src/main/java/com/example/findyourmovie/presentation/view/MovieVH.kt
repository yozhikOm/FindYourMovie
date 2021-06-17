package com.example.findyourmovie.presentation.view

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findyourmovie.R
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.utils.Helper

class HeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val header = itemView.findViewById<TextView>(R.id.header)

    fun bind() {
        if (itemView.context as Activity is MainActivity) {
            header.setText(R.string.movies)
        //TODO как задать др.заголовок во фрагменте?
        } else if (itemView.context as Fragment is FavoritesListFragment) {
            header.setText(R.string.favorites)
        }
    }
}

class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.movieTitle)
    private val favIcon = itemView.findViewById<ImageView>(R.id.favoriteImg)
    private val details = itemView.findViewById<TextView>(R.id.details)
    private val poster = itemView.findViewById<ImageView>(R.id.poster)

    fun bind(movie: Movie) {
        //TODO как определить, какой фрагмент сейчас активный?
        if (itemView.context as Activity is MainActivity) {
        //if (itemView.context as Fragment is MovieListFragment) {
            if (movie.isFavorite) {
                favIcon.setImageResource(R.drawable.ic_heart_red_24dp)
            } else {
                favIcon.setImageResource(R.drawable.ic_heart_grey_24dp)
            }
        }
        else {//if (itemView.context as Fragment is FavoritesListFragment) {
            favIcon.setImageResource(R.drawable.ic_delete_red_24dp)
        }

        if (movie.isVisited) {
            title.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorVisited))
        }
        else {
            title.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
        }

        title.text = movie.title
        details.text = movie.details
        if(movie.posterPath != null) {
            val imgUrl: String = Helper.getMetaData(itemView.context, "img_url")!!
            Glide.with(itemView.context).load("${imgUrl}${movie.posterPath}").into(poster)
        }
        else {
            poster.setImageResource(R.drawable.ic_baseline_local_movies_24)
        }
    }

}
