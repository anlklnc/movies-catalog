package com.anil.moviescatalog.ui.movieDetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.anil.moviescatalog.model.Movie

@Composable
fun MovieDetailsScreen (
    movie: Movie
) {
    Text(movie.title)
}