package com.anil.moviescatalog.data

import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movies
import com.anil.moviescatalog.network.MoviesApi
import com.anil.moviescatalog.network.NetworkUtils.safeApiCall
import com.anil.moviescatalog.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesRepository {
    suspend fun getMovieList(category: Category, page: Int): Result<Movies>
}

class NetworkMoviesRepository(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getMovieList(category: Category, page: Int): Result<Movies> = withContext(Dispatchers.IO) {
        safeApiCall {
            val sortBy = when (category) {
                Category.POPULAR -> "popularity.desc"
                Category.TOP_RATED -> "vote_average.desc"
                Category.REVENUE -> "revenue.desc"
            }
            moviesApi.getMovieList(sortBy, page)
        }
    }
}