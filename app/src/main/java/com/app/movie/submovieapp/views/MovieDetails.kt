package com.app.movie.submovieapp.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

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
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val serviceMovie : MovieService = retrofit.create(MovieService::class.java)

        serviceMovie.getMovieById(movieIdExtra.toString()).also {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    if (it == null) finish()
                    it.await().also {
                        val uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + it.poster_path)
                        _movie_cover.setImageURI(uri, null)
                        _movie_title.text = it.title
                        _movie_overview.text = it.overview
                    }
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
