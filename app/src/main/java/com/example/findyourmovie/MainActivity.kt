package com.example.findyourmovie

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieClickListener {
    companion object {
        private const val RESULT_CODE_DETAILS = 333
    }

    //region items lists
    private var items = mutableListOf(
        MovieItem(
            1,
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            2,
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            3,
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            4,
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            5,
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            6,
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            7,
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            8,
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            9,
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            10,
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            11,
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            12,
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            13,
            "Мортал Комбат",
            "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
            R.drawable.mortal_combat,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            14,
            "Поступь хаоса",
            "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
            R.drawable.chaos_walking,
            isFavorite = false,
            isVisited = false
        ),
        MovieItem(
            15,
            "100% Волк",
            "«100% Волк» — австралийский полнометражный комедийный мультфильм Алекса Стадерманна. Фредди Люпин — наследник гордого семейства оборотней, отчаянно желающий стать полноценным членом стаи. Однако в день своего долгожданного 13-летия он превращается в пуделя. Думая, что жизнь не может стать еще хуже, он попадает в собачий приют, где знакомится с собакой по кличке Бетти",
            R.drawable.wolf,
            isFavorite = false,
            isVisited = false
        )
    )
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO почему приложение крэшится при переворачивании экрана?

//        savedInstanceState?.let {
//            items = it.getParcelableArrayList("EXTRA_ITEMS")!!
//        }
        showMovieList()
    }

    private fun showMovieList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieListFragment(items), MovieListFragment.TAG)
            .commit()
    }

    private fun showMovieDetails(item: MovieItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieDetailsFragment.newInstance(item), MovieDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun showFavoritesList() {
        val favoriteItems = items.filter{it.isFavorite}.toMutableList()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FavoritesListFragment(favoriteItems), FavoritesListFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        outState.putParcelableArrayList("EXTRA_ITEMS", ArrayList(items))
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == RESULT_CODE_DETAILS) {
//            if(resultCode == Activity.RESULT_OK) {
//                val favItems =  data?.getSerializableExtra("EXTRA_FAVORITES_LIST") as ArrayList<MovieItem>
//                items.forEach { movie ->
//                    val foundItem = favItems.firstOrNull { fav ->
//                        fav.id == movie.id
//                    }
//                    movie.isFavorite = foundItem != null
//                }
//                //initRecycler()
//            }
//        }
//    }

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //TODO доработать переключение языков
    private fun changeLocale(locale: Locale) {
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_en -> {
                val locale = Locale("en", "US")
                changeLocale(locale)

                true
            }
            R.id.action_ru -> {
                val locale = Locale("ru", "RU")
                changeLocale(locale)

                true
            }
            R.id.action_favorites -> {
                showFavoritesList()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val favFragment: FavoritesListFragment? =
                supportFragmentManager.findFragmentByTag("FavoritesListFragment") as FavoritesListFragment?
            if (favFragment != null && favFragment.isVisible) {
                Toast.makeText(this, "it was FavoritesListFragment", Toast.LENGTH_SHORT).show()
                val favItems =  favFragment.items
                items.forEach { movie ->
                    val foundItem = favItems.firstOrNull { fav ->
                        fav.id == movie.id
                    }
                    movie.isFavorite = foundItem != null
                }

            }
//            else {
//                Toast.makeText(this, "it was not FavoritesListFragment", Toast.LENGTH_SHORT).show()
//            }

            supportFragmentManager.popBackStack()
        } else {
            ExitDialog().show(supportFragmentManager, "dialog")
        }
    }

    override fun onDetailsClick(movieItem: MovieItem, position: Int) {
        showMovieDetails(movieItem)
    }

    //TODO корректо ли оставлять этот метод пустым и все изменения с элементом производить во фрагменте?
    // или стоит передавать в этот метод еще и ресайклер и здесь производить изменения с элементом?
    override fun onFavoriteClick(movieItem: MovieItem, position: Int) {//, recycler: RecyclerView) {
//        movieItem.isFavorite = !movieItem.isFavorite
//        recycler.adapter?.notifyItemChanged(position)
    }


}
