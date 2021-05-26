package com.example.findyourmovie

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DetailsActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val EXTRA_COMMENT = "EXTRA_COMMENT"
        private const val EXTRA_IS_LIKED = "EXTRA_IS_LIKED"
    }

    //region views by lazy
    private val titleTextView by lazy {
        findViewById<TextView>(R.id.titleTxtView)
    }
    private val detailsTextView by lazy {
        findViewById<TextView>(R.id.detailsTxtView)
    }
    private val coverImgView by lazy {
        findViewById<ImageView>(R.id.coverImg)
    }

    private val inviteFriendBtn by lazy {
        findViewById<Button>(R.id.inviteFriendBtn)
    }

    private val likeItCheckBox by lazy {
        findViewById<CheckBox>(R.id.likeItCheckBox)
    }

    private val backBtn by lazy {
        findViewById<Button>(R.id.backBtn)
    }

    private val commentEdtView by lazy {
        findViewById<TextView>(R.id.commentTxtView)
    }
//endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie: MovieItem = intent.getParcelableExtra(EXTRA_MOVIE)
        titleTextView.text = movie.title
        detailsTextView.text = movie.details
        coverImgView.setImageResource(movie.cover)

        inviteFriendBtn.setOnClickListener {
             val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hey, buddy, I recommend you to watch this movie! - " + titleTextView.text.toString())
                type = "text/plain"
            }
            startActivity(sendIntent)
        }

        backBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_IS_LIKED, likeItCheckBox.isChecked.toString())
            intent.putExtra(EXTRA_COMMENT, commentEdtView.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

    }

}