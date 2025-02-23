package com.anil.moviescatalog.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anil.moviescatalog.data.MoviesRepository
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    private val _popularMovies = MutableStateFlow<Movies?>(null)
    val popularMovies: StateFlow<Movies?> = _popularMovies.asStateFlow()
    private val _topRatedMovies = MutableStateFlow<Movies?>(null)
    val topRatedMovies: StateFlow<Movies?> = _topRatedMovies.asStateFlow()
    private val _topEarnerMovies = MutableStateFlow<Movies?>(null)
    val topEarnerMovies: StateFlow<Movies?> = _topEarnerMovies.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _popularMovies.value = repository.getMovieList(Category.POPULAR)
            _topRatedMovies.value = repository.getMovieList(Category.TOP_RATED)
            _topEarnerMovies.value = repository.getMovieList(Category.REVENUE)
        }
    }
}