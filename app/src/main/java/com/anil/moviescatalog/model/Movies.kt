package com.anil.moviescatalog.model

import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)