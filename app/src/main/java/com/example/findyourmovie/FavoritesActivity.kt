package com.example.findyourmovie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class FavoritesActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_FAVORITES_LIST = "EXTRA_FAVORITES_LIST"
    }

    private val recyclerFav by lazy { findViewById<RecyclerView>(R.id.recyclerFavoritesView) }
    private var favoriteItems: MutableList<MovieItem> = ArrayList<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val favItems =  intent.getSerializableExtra("EXTRA_FAVORITES_LIST") as ArrayList<MovieItem>
        favoriteItems = favItems.toMutableList()
        initRecycler()
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        recyclerFav.layoutManager = layoutManager;

        recyclerFav.adapter = MovieAdapter(favoriteItems as ArrayList<MovieItem>) { item, position ->
            //Toast.makeText(this, "clicked ${item.title} $position", Toast.LENGTH_SHORT).show()

            favoriteItems.remove(item)
            recyclerFav.adapter?.notifyItemRemoved(position)
        }

        val itemDecoration = CustomItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.line_4dp_grey)!!)
        recyclerFav.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(EXTRA_FAVORITES_LIST, favoriteItems as Serializable)
        setResult(RESULT_OK, intent)
        finish()
    }

}