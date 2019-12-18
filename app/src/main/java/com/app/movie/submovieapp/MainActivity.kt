package com.app.movie.submovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.app.movie.submovieapp.adapter.MovieAdapter
import com.app.movie.submovieapp.models.Movie
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import com.facebook.drawee.backends.pipeline.Fresco
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.await

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        val movies = ArrayList<Movie>()
        val serviceMovie : MovieService = retrofit.create(MovieService::class.java)

        serviceMovie.getMovieById("500").also {
            GlobalScope.launch {
                it.await().also {
                    Log.d("debug", "Titre : ${it.title}")
                }
            }
        }

        serviceMovie.getPopularMovies().also {
            GlobalScope.launch {
                it.await().results.also {
                    withContext(Dispatchers.Main) {
                        _listView.layoutManager = GridLayoutManager(baseContext, 2)
                        val movieAdapter = MovieAdapter(it)
                        _listView.adapter = movieAdapter
                    }
                }
            }
        }
    }

    /**
     * Initialise Realm Configuration and get Realm Instance
     *
     * @return Realm
     */
    private fun getRealmInstance () {
        Realm.init(this)
        val realmConfiguration : RealmConfiguration = RealmConfiguration.Builder()
            .name("movie.realm")
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}