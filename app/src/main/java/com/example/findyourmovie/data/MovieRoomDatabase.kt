package com.example.findyourmovie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.findyourmovie.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(MovieDB::class), version = 1)
abstract class MovieRoomDatabase(): RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MovieRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movies_database.db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(MovieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class MovieDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.getMovieDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more movies, just add them.
         */
        fun populateDatabase(movieDao: MovieDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            movieDao.deleteAll()

//            var movie = MovieDB(
//                title = "Мортал Комбат",
//                details = "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку.",
//                cover =  R.drawable.mortal_combat,
//                isFavorite = true,
//                isVisited = false
//            )
//            movieDao.addMovie(movie)
//
//            movie = MovieDB(
//                title = "Поступь хаоса",
//                details = "2257 год. Родина Тодда Хьюитта — колонизированная планета Новый Мир, где мысли мужчин имеют визуально-звуковое воплощение и называются шумом. Парень живёт в небольшой деревне, населённой исключительно мужчинами, и ещё не научился скрывать свои мысли от окружающих. Однажды над планетой терпит крушение корабль-разведчик из второй волны колонистов, в результате чего Тодд впервые в сознательной жизни видит девушку. Местный мэр решет использовать её в своих коварных планах, но девушка сбегает, и теперь без помощи Тодда ей не обойтись.",
//                cover = R.drawable.chaos_walking,
//                isFavorite = false,
//                isVisited = false
//            )
//            movieDao.addMovie(movie)
        }
    }

}
