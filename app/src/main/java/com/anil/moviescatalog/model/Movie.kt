package com.anil.moviescatalog.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val adult: Boolean = true,
    val id: Int? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
)