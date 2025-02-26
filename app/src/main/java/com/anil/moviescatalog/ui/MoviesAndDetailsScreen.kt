package com.anil.moviescatalog.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.movieDetails.MovieDetailsScreen
import com.anil.moviescatalog.ui.movies.MoviesScreen
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme

@Composable
fun MoviesAndDetailsScreen(
    selectedMovie: Movie?,
    onMovieSelected: (Movie) -> Unit,
    onNavigateToStreaming: (Movie) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        MoviesScreen(
            Modifier.weight(1f),
            onNavigateToDetails = onMovieSelected
        )
        selectedMovie?.let {
            MovieDetailsScreen(
                it,
                Modifier.weight(1f),
                onNavigateToStreaming = { onNavigateToStreaming(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesAndDetailsScreenPreview() {
    MoviesCatalogTheme {
        MoviesAndDetailsScreen(
            selectedMovie = Movie(
                id = 1,
                title = "Movie Title",
                overview = "Movie Overview",
                poster_path = "Movie Poster Path",
                release_date = "1/10/2013",
                vote_average = 5.0,
                video = true,
                adult = false
            ),
            onMovieSelected = {},
            onNavigateToStreaming = {}
        )
    }
}