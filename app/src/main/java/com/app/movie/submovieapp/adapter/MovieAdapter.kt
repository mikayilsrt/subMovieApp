package com.app.movie.submovieapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.movie.submovieapp.R
import com.app.movie.submovieapp.models.Movie
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
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + movie.backdrop_path)

            view._singleTitle.text = movie.title
            view._movie_image.setImageURI(uri)
        }

    }
}