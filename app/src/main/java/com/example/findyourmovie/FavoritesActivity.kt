package com.example.findyourmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class FavoritesActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_FAVORITES_LIST = "EXTRA_FAVORITES_LIST"

        fun launchActivity(favList: ArrayList<MovieItem>, context: Context) {
            context.startActivity(Intent(context, FavoritesActivity::class.java).apply {
                putExtra("EXTRA_FAVORITES_LIST", favList as Serializable);
                context.startActivity(this)
            })
        }
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
            Toast.makeText(this, "clicked ${item.title} $position", Toast.LENGTH_SHORT).show()

            favoriteItems.remove(item)
            recyclerFav.adapter?.notifyItemRemoved(position)

        }

//        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if(layoutManager.findLastVisibleItemPosition() >= items.size - 5) {
//                    repeat(4){
//                        items.add(MovieItem("---", "some details", R.drawable.wolf, false))
//                    }
//                    recycler.adapter?.notifyItemRangeChanged(items.size - 4, 4)
//                }
//            }
//        })

        //val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //itemDecoration.setDrawable(getDrawable(R.drawable.line_4dp_grey)!!)
        //val itemDecoration = MainActivity.CustomItemDecoration(this, DividerItemDecoration.VERTICAL)
        //itemDecoration.setDrawable(getDrawable(R.drawable.line_4dp_grey)!!)
        //recycler.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        val intent: Intent = Intent(applicationContext, MainActivity::class.java);
        startActivity(intent);
    }
}