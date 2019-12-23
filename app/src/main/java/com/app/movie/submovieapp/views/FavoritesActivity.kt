package com.app.movie.submovieapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.movie.submovieapp.R

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }
}
