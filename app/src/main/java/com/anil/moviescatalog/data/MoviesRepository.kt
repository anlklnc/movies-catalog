package com.anil.moviescatalog.data

import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.model.Movies
import com.anil.moviescatalog.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesRepository {
    suspend fun getMovieList(category: Category, page: Int): Movies
    suspend fun getMovie(): Movie
}

class NetworkMoviesRepository(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getMovieList(category: Category, page: Int) = withContext(Dispatchers.IO) {
        val sortBy = when (category) {
            Category.POPULAR -> "popularity.desc"
            Category.TOP_RATED -> "vote_average.desc"
            Category.REVENUE -> "revenue.desc"
        }
        moviesApi.getMovieList(sortBy, page)
    }
    override suspend fun getMovie() = withContext(Dispatchers.IO) {
        moviesApi.getMovie()
    }
}