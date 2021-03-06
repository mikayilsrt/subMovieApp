package com.app.movie.submovieapp.views

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.adapter.MovieAdapter
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import com.facebook.drawee.backends.pipeline.Fresco
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.await

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        if (this.checkNetworkConnexion()) {
            val serviceMovie : MovieService = retrofit.create(MovieService::class.java)

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
    }

    /**
     * Specify options menu for main activity
     *
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites_movie, menu)
        menuInflater.inflate(R.menu.menu_search_movie, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Select menu item
     *
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id._search_menu_button -> {
                startSearchActivity()
                true
            }
            R.id._favorite_menu_button -> {
                startFavoritesActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Check if mobile connexion or wifi is disabled or not
     *
     * @return Boolean
     */
    private fun checkNetworkConnexion() : Boolean {
        val connManager : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConn = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileDataConn = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return wifiConn.isConnectedOrConnecting || mobileDataConn.isConnectedOrConnecting
    }

    /**
     * Start search activity
     */
    private fun startSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    /**
     * Start favorite activity
     */
    private fun startFavoritesActivity() {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }
}