package com.app.movie.submovieapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.models.Movie
import com.app.movie.submovieapp.views.MovieDetails
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.row.view.*

class MovieAdapter(
    private val list : List<Movie>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {

        fun bind(movie : Movie) {
            val fromBottom : Animation = AnimationUtils.loadAnimation(view.context, R.anim.frombottom)
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + movie.backdrop_path)

            view.animation = fromBottom
            view._singleTitle.text = movie.title
            view._movie_image.setImageURI(uri, null)

            isFavorite(movie, view)

            view._favoriteButton.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                movie.save()

                bind(movie)
            }

            view._movie_row_layout.setOnClickListener {
                MovieDetails.start(it.context, movie.id)
            }
        }

    }

    /**
     * Change Favorite icon state
     *
     * @param Movie movie
     *
     * @param View view
     */
    private fun isFavorite(movie : Movie, view : View) {
        if (movie.isFavorite) {
            view._favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            view._favoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }
}