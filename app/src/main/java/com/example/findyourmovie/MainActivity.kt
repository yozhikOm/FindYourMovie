package com.example.findyourmovie

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val recycler by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    //region items lists
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
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
    }

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //TODO такое переключение не работает
    //TODO как правильно переключаться между языками, если updateConfiguration - deprecated ?
    //TODO почему по умолчанию грузится английский, когда в стрингах дефолтный - русский ?
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

                //Configuration().setLocale(Locale.ENGLISH);

                true
            }
            R.id.action_ru -> {
                val locale = Locale("ru", "RU")
                //changeLocale(locale)
                //TODO так тоже не работает
                //resources.configuration.setLocale(locale);

                true
            }
            R.id.action_favorites -> {
                FavoritesActivity.launchActivity(ArrayList(favoriteItems), this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //TODO почему не триггерится onConfigurationChanged?
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        val gridLayoutManager = GridLayoutManager(this, 2)

        //TODO не меняет на portrait, когда вверх ногами, потому что эмулятор?
        val orientation = this.resources.configuration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycler.layoutManager = linearLayoutManager;
        } else {
            recycler.layoutManager = gridLayoutManager;
        }

        recycler.adapter = MovieAdapter(items) { item, position ->
            //Toast.makeText(this, "clicked ${item.title} $position", Toast.LENGTH_SHORT).show()
            item.isFavorite = !item.isFavorite
            recycler.adapter?.notifyItemChanged(position)

            if (item.isFavorite) {
                favoriteItems.add(item)
            } else {
                favoriteItems.remove(item)
            }
        }

        val itemDecoration = CustomItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.line_4dp_grey)!!)
        recycler.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        ExitDialog().show(supportFragmentManager, "dialog")
    }


}
