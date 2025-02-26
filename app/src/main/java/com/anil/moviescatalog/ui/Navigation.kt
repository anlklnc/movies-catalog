package com.anil.moviescatalog.ui

import com.anil.moviescatalog.model.Movie
import kotlinx.serialization.Serializable

class Navigation {
    @Serializable
    object MoviesRoute

    @Serializable
    object MoviesTabletRoute

    @Serializable
    data class MovieDetailsRoute(val movie: Movie)

    @Serializable
    data class StreamingRoute(val movie: Movie)
}