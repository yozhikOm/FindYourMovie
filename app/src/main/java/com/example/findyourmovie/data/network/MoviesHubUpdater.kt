package com.example.findyourmovie.data.network

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.findyourmovie.data.PopularMovies
import com.example.findyourmovie.utils.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MoviesHubUpdater"
private const val DELAY = 5000L

class MoviesHubUpdater(private val context: Context, private val service: MoviesHubAPI) :
    LifecycleObserver {
    private val handler = Handler()

    fun setLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        //if (lifecycle.currentState.isAtLeast())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifecycleResume() {
        Log.d(TAG, "onResume")

        handler.postDelayed(GetMoviesRunnable(), DELAY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifecyclePaused() {
        Log.d(TAG, "onPause")

        handler.removeCallbacksAndMessages(null)
    }

    inner class GetMoviesRunnable : Runnable {
        override fun run() {
            service.getMovies(Helper.getMetaData(context, "api_key")!!)
                .enqueue(object : Callback<PopularMovies> {
                    override fun onResponse(
                        call: Call<PopularMovies>,
                        response: Response<PopularMovies>
                    ) {
                        handler.postDelayed(GetMoviesRunnable(), DELAY)
                    }

                    override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                        Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

}
