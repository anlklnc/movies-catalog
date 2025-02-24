package com.anil.moviescatalog.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val adult: Boolean,
    val id: Int,
    val overview: String,
    val poster_path: String? = null,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
)