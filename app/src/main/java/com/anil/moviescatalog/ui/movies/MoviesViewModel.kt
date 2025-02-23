package com.anil.moviescatalog.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anil.moviescatalog.data.MoviesRepository
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
    val name = "Movies"

    private val _movieList = MutableStateFlow<Movies?>(null)
    val movieList: StateFlow<Movies?> = _movieList.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            val movies = repository.getMovieList()
            _movieList.value = movies
        }
    }
}