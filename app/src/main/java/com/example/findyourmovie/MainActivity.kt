package com.example.findyourmovie

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnDetailsClickListener {
    companion object {
        private const val ITEMS = "ITEMS"
        private const val ACTIVE_FRAGMENT = "ACTIVE_FRAGMENT"
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

    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ITEMS)!!

            val activeFragmentTag = savedInstanceState.getString(ACTIVE_FRAGMENT)
            if (activeFragmentTag == null) {
                showMovieList()
            } else if (activeFragmentTag == "FavListFragment") {
                showFavoritesList()
            }
        } else {
            showMovieList()
        }
    }

    private fun showMovieList() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                MovieListFragment.newInstance(items),
                MovieListFragment.TAG
            )
            .commit()
    }

    private fun showMovieDetails(item: MovieItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                MovieDetailsFragment.newInstance(item),
                MovieDetailsFragment.TAG
            )
            .addToBackStack("DetailsFragment")
            .commit()
    }

    private fun showFavoritesList() {
        val favoriteItems = items.filter { it.isFavorite }.toMutableList()

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FavoritesListFragment.newInstance(favoriteItems),
                FavoritesListFragment.TAG
            )
            .addToBackStack("FavListFragment")
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(ITEMS, ArrayList(items))

        val activeFragmentTag: String? = getVisibleFragmentTag()
        outState.putString(ACTIVE_FRAGMENT, activeFragmentTag)
    }

    private fun getVisibleFragmentTag(): String? {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment.tag
            }
        }
        return null
    }

    override fun onDetailsClick(movieItem: MovieItem, position: Int) {
        showMovieDetails(movieItem)
    }

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

            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //region Botton Navigation
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigationHome -> {
                    showMovieList()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationFavorites -> {
                    showFavoritesList()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    //endregion

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            when (getVisibleFragmentTag()) {
                "MovieListFragment" -> ExitDialog().show(supportFragmentManager, "dialog")
                "FavoritesListFragment" -> {
                    val favFragment: FavoritesListFragment =
                        supportFragmentManager.findFragmentByTag("FavoritesListFragment") as FavoritesListFragment
                    val favItems = favFragment.items
                    items.forEach { movie ->
                        val foundItem = favItems.firstOrNull { fav ->
                            fav.id == movie.id
                        }
                        movie.isFavorite = foundItem != null
                    }
                }
            }
            supportFragmentManager.popBackStack()
        } else {
            ExitDialog().show(supportFragmentManager, "dialog")
        }
    }


}
