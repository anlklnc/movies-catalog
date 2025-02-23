package com.anil.moviescatalog.data

import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.model.Movies
import com.anil.moviescatalog.network.MoviesApi

interface MoviesRepository {
    suspend fun getMovieList(): Movies
    suspend fun getMovie(): Movie
}

class NetworkMoviesRepository(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getMovieList() = moviesApi.getMovieList()
    override suspend fun getMovie() = moviesApi.getMovie()
}