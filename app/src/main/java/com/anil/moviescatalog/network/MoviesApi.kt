package com.anil.moviescatalog.network

import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.model.Movies
import retrofit2.http.GET

interface MoviesApi {
    @GET("3/discover/movie")
    suspend fun getMovieList(): Movies

    @GET("3/genre/movie/list")
    suspend fun getMovie(): Movie
}