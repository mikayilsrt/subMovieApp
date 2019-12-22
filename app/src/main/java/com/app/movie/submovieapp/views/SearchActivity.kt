package com.app.movie.submovieapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.adapter.SearchAdapter
import com.app.movie.submovieapp.services.MovieService
import com.app.movie.submovieapp.services.RetrofitHolder.retrofit
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        _search_input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(string: Editable?) {
                displayMoviesFromSearch(string.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        })
    }

    /**
     * Display movie item from search query
     *
     * @param string search query
     */
    private fun displayMoviesFromSearch(string : String) {
        if (string.isEmpty()) return

        val serviceMovie : MovieService = retrofit.create(MovieService::class.java)
        serviceMovie.getMoviesFromQuery(string).also {
            GlobalScope.launch {
                it.await().results.also {
                    withContext(Dispatchers.Main) {
                        _searchListView.layoutManager = GridLayoutManager(baseContext, 2)
                        val searchMovieAdapter = SearchAdapter(it)
                        _searchListView.adapter = searchMovieAdapter
                    }
                }
            }
        }
    }
}
