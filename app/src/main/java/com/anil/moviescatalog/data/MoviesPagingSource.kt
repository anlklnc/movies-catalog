package com.anil.moviescatalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie

class MoviesPagingSource(
    private val repository: MoviesRepository,
    private val category: Category
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = repository.getMovieList(category, nextPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.results.isEmpty()) null else nextPage + 1
            )
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