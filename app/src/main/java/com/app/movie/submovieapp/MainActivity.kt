package com.app.movie.submovieapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceMovie : MovieService = retrofit.create(MovieService::class.java)

        serviceMovie.getMovieById("500").also {
            GlobalScope.launch {
                it.await().also {
                    Log.d("debug", "Titre : ${it.title}")
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