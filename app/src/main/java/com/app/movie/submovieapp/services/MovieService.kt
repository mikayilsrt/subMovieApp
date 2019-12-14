package com.app.movie.submovieapp.services

import com.app.movie.submovieapp.models.Movie
import retrofit2.Call
import retrofit2.http.*

interface MovieService {

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id : String,
        @Query("api_key") apiKey : String = "66ee102c15b779b77afde5b5948b26c4"
    ) : Call<Movie>

}