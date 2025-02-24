package com.anil.moviescatalog.ui

import com.anil.moviescatalog.model.Movie
import kotlinx.serialization.Serializable

class Navigation {
    @Serializable
    object MoviesRoute

    @Serializable
    data class MovieDetailsRoute(val movie: Movie)
}