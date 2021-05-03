package com.example.findyourmovie

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyActivity"

class MainActivity : AppCompatActivity() {

    // region old code
//    companion object {
//        private const val RESULT_CODE_DETAILS = 333
//    }
//
//    private val detailsBtn1 by lazy {
//        findViewById<Button>(R.id.detailsBtn1)
//    }
//    private val titleTxtView1 by lazy {
//        findViewById<TextView>(R.id.titleTxtView1)
//    }
//    private val detailsBtn2 by lazy {
//        findViewById<Button>(R.id.detailsBtn2)
//    }
//    private val titleTxtView2 by lazy {
//        findViewById<TextView>(R.id.titleTxtView2)
//    }
//    private val detailsBtn3 by lazy {
//        findViewById<Button>(R.id.detailsBtn3)
//    }
//    private val titleTxtView3 by lazy {
//        findViewById<TextView>(R.id.titleTxtView3)
//    }
//
//    private val aboutMovies: Array<String> = arrayOf(
//        "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
//        "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
//        "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти"
//    )
//
//    var movieCovers= arrayOf(R.drawable.mortal_combat, R.drawable.chaos_walking, R.drawable.wolf)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        savedInstanceState?.let {
//            titleTxtView1.setTextColor(it.getInt("EXTRA_COLOR1"))
//            titleTxtView2.setTextColor(it.getInt("EXTRA_COLOR2"))
//            titleTxtView3.setTextColor(it.getInt("EXTRA_COLOR3"))
//        }
//
//        detailsBtn1.setOnClickListener{
//            titleTxtView1.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
//
//            //TODO как совместить startActivityForResult с launchActivity ?
//            val intent = Intent(this, DetailsActivity::class.java)
//            startActivityForResult(intent, RESULT_CODE_DETAILS)
//            //DetailsActivity.launchActivity(titleTxtView1.text.toString(), aboutMovies[0], movieCovers[0],this)
//        }
//
//        detailsBtn2.setOnClickListener{
//            titleTxtView2.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
//
//            DetailsActivity.launchActivity(titleTxtView2.text.toString(), aboutMovies[1], movieCovers[1], this)
//        }
//
//        detailsBtn3.setOnClickListener{
//            titleTxtView3.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
//
//            DetailsActivity.launchActivity(titleTxtView3.text.toString(), aboutMovies[2], movieCovers[2],this)
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        outState.putInt("EXTRA_COLOR1", titleTxtView1.currentTextColor)
//        //TODO как сохранять текущий цвет для каждого элемента, чтобы не плодить переменные?
//        outState.putInt("EXTRA_COLOR2", titleTxtView2.currentTextColor)
//        outState.putInt("EXTRA_COLOR3", titleTxtView3.currentTextColor)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == RESULT_CODE_DETAILS) {
//            if(resultCode == Activity.RESULT_OK) {
//                Log.i(TAG, data?.getStringExtra("EXTRA_IS_LIKED"))
//                Log.i(TAG, data?.getStringExtra("EXTRA_COMMENT"))
//                Toast.makeText(this, data?.getStringExtra("EXTRA_IS_LIKED")?: "not liked", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, data?.getStringExtra("EXTRA_COMMENT")?: "no comments", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    // endregion

    private val recycler by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    private val items = mutableListOf(
        MovieItem(
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            false,
            false
        ),
        MovieItem(
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            false,
            false
        ),
        MovieItem(
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            false,
            false
        ),
        MovieItem(
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            false,
            false
        ),
        MovieItem(
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            false,
            false
        ),
        MovieItem(
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            false,
            false
        ),
        MovieItem(
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            false,
            false
        ),
        MovieItem(
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            false,
            false
        ),
        MovieItem(
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            false,
            false
        ),
        MovieItem(
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            false,
            false
        ),
        MovieItem(
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            false,
            false
        ),
        MovieItem(
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            false,
            false
        ),
        MovieItem(
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            false,
            false
        ),
        MovieItem(
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            false,
            false
        ),
        MovieItem(
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            false,
            false
        )
    )

    private var favoriteItems = mutableListOf<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                FavoritesActivity.launchActivity(ArrayList(favoriteItems), this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager;

        recycler.adapter = MovieAdapter(items) { item, position ->
            //Toast.makeText(this, "clicked ${item.title} $position", Toast.LENGTH_SHORT).show()
            item.isFavorite = !item.isFavorite
            //item.isVisited = true
            recycler.adapter?.notifyItemChanged(position)

            if (item.isFavorite) {
                favoriteItems.add(item)
            } else {
                favoriteItems.remove(item)
            }
            Toast.makeText(this, "favoriteItems.Count = ${favoriteItems.size}", Toast.LENGTH_SHORT)
                .show()
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
        val itemDecoration = CustomItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.line_4dp_grey)!!)
        recycler.addItemDecoration(itemDecoration)
    }

    class CustomItemDecoration(context: Context, orientation: Int) :
        DividerItemDecoration(context, orientation) {
        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            //super.getItemOffsets(outRect, view, parent, state)
        }
    }

}
