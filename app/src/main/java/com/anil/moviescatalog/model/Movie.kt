package com.anil.moviescatalog.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val adult: Boolean,
    val id: Int,
    val overview: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean,
    val vote_average: Double,
)