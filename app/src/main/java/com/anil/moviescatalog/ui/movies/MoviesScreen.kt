package com.anil.moviescatalog.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anil.moviescatalog.R
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

const val ITEM_IMAGE_SIZE = 200

@Composable
fun MoviesScreen(modifier: Modifier = Modifier, vm: MoviesViewModel = viewModel()) {
    val movies by vm.movieList.collectAsState()
    movies?.let {
        MoviesScreenContent(modifier, it.results, vm.name)
    }
}

@Composable
fun MoviesScreenContent(modifier: Modifier = Modifier, movies: List<Movie>, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Red))
        Text(text = name)
        movies.let {
            LazyRow {
                items(it) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie) {
    Column {
        GlideImage(
            model = "https://image.tmdb.org/t/p/w$ITEM_IMAGE_SIZE${movie.poster_path}",
            contentDescription = null,
            modifier = Modifier.size(ITEM_IMAGE_SIZE.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesPreview() {
    MoviesCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MoviesScreenContent(movies = listOf(), name = "Moviezx")
        }
    }
}