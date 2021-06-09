package com.example.findyourmovie.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.findyourmovie.R
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.data.MovieItem

class MovieDetailsFragment: Fragment() {
    companion object {
        const val TAG = "MovieDetailsFragment"
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val EXTRA_COMMENT = "EXTRA_COMMENT"
        private const val EXTRA_IS_LIKED = "EXTRA_IS_LIKED"

        fun newInstance(movieItem: Movie): MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_MOVIE, movieItem)
            
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
// или
//        fun newInstanceKotlin(title: String): MovieDetailsFragment {
//            return MovieDetailsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(EXTRA_TITLE, title)
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieItem: Movie? = arguments?.getParcelable(EXTRA_MOVIE)

        view.findViewById<TextView>(R.id.titleTxtView).text = movieItem?.title
        view.findViewById<TextView>(R.id.detailsTxtView).text = movieItem?.details
        movieItem?.cover?.let {
            view.findViewById<ImageView>(R.id.coverImg).setImageResource(
                it
            )
        }

        view.findViewById<View>(R.id.inviteFriendBtn).setOnClickListener {
            val movieItem: MovieItem? = arguments?.getParcelable<MovieItem>(EXTRA_MOVIE)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hey, buddy, I recommend you to watch this movie! - " + movieItem?.title)
                type = "text/plain"
            }
            startActivity(sendIntent)
        }

    }
}