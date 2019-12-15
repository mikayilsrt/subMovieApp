package com.app.movie.submovieapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.app.movie.submovieapp.models.Movie
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        serviceMovie.getPopularMovies().enqueue(object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) { Log.d("debug", "Load failed") }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("debug", "response : ${response.raw()}")
            }

        })

        _listView.adapter = ArrayAdapter(this, R.layout.row, movies)
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