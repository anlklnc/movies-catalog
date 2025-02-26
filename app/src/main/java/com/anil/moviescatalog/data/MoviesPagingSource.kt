package com.anil.moviescatalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.network.Result

class MoviesPagingSource(
    private val repository: MoviesRepository,
    private val category: Category,
    private val error: (Exception) -> Unit
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            when (val response = repository.getMovieList(category, nextPage)) {
                is Result.Success -> {
                    LoadResult.Page(
                        data = response.data.results,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (response.data.results.isEmpty()) null else nextPage + 1
                    )
                }
                is Result.Error -> {
                    error(response.exception)
                    LoadResult.Error(response.exception)
                }
                else ->{
                    val exception = Exception("Network error: ${response::class.simpleName}")
                    error(exception)
                    LoadResult.Error(exception)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}