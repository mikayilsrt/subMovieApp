package com.app.movie.submovieapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.adapter.FavoriteAdapter
import com.app.movie.submovieapp.models.Movie
import com.vicpin.krealmextensions.query
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        Movie().query { equalTo("isFavorite", true) }.also {
            _favoritesListView.layoutManager = GridLayoutManager(baseContext, 2)
            val favoritesAdapter = FavoriteAdapter(it)
            _favoritesListView.adapter = favoritesAdapter
        }

    }
}
