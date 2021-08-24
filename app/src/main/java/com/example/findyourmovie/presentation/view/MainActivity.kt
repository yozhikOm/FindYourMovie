package com.example.findyourmovie.presentation.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.findyourmovie.R
import com.example.findyourmovie.data.Movie
import com.example.findyourmovie.presentation.viewmodel.MovieViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnDetailsClickListener {
    companion object {
        private const val ACTIVE_FRAGMENT = "ACTIVE_FRAGMENT"
    }

    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState != null) {
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
                MovieListFragment(),
                MovieListFragment.TAG
            )
            .commit()
    }

    private fun showMovieDetails(item: Movie) {
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
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FavoritesListFragment(),
                FavoritesListFragment.TAG
            )
            .addToBackStack("FavListFragment")
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

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

    override fun onDetailsClick(movieItem: Movie, position: Int) {
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
            }
            supportFragmentManager.popBackStack()
        } else {
            ExitDialog().show(supportFragmentManager, "dialog")
        }
    }


}
