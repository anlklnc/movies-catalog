package com.anil.moviescatalog.network

import com.anil.moviescatalog.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/discover/movie")
    suspend fun getMovieList(@Query("sort_by")sortBy: String, @Query("page")page: Int): Movies
}