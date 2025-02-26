package com.anil.moviescatalog.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anil.moviescatalog.data.MoviesPagingSource
import com.anil.moviescatalog.data.MoviesRepository
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 20

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    lateinit var popularMovies: Flow<PagingData<Movie>>
    lateinit var topRatedMovies: Flow<PagingData<Movie>>
    lateinit var topEarnerMovies: Flow<PagingData<Movie>>

    // Event for handling network errors on the ui side
    private val _error = MutableSharedFlow<Exception>()
    val error: SharedFlow<Exception> = _error

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            popularMovies = createPagerFlow(Category.POPULAR)
            topRatedMovies = createPagerFlow(Category.TOP_RATED)
            topEarnerMovies = createPagerFlow(Category.REVENUE)
        }
    }

    private fun createPagerFlow(category: Category): Flow<PagingData<Movie>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            MoviesPagingSource(repository, category) {
                // Handle error
                viewModelScope.launch {
                    _error.emit(it)
                }
            }
        }.flow.cachedIn(viewModelScope)
}