package com.app.movie.submovieapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.movie.submovieapp.R

class MovieDetails : AppCompatActivity() {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun start(context : Context, id : Int) {
            context.startActivity(
                Intent(context, MovieDetails::class.java).apply {
                    putExtra(EXTRA_MOVIE_ID, id)
                }
            )
        }
    }

    private val movieIdExtra by lazy {
        intent?.getIntExtra("EXTRA_MOVIE_ID", -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
    }
}
