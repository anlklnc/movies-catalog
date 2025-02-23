package com.anil.moviescatalog.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

const val ITEM_IMAGE_HEIGHT = 200
const val ITEM_IMAGE_WIDTH = 140

@Composable
fun MoviesScreen(modifier: Modifier = Modifier, vm: MoviesViewModel = viewModel()) {
    val popularMovies by vm.popularMovies.collectAsState()
    val topRatedMovies by vm.topRatedMovies.collectAsState()
    val topEarnerMovies by vm.topEarnerMovies.collectAsState()
    MoviesScreenContent(modifier,
        popularMovies?.results,
        topRatedMovies?.results,
        topEarnerMovies?.results)
}

@Composable
fun MoviesScreenContent(modifier: Modifier = Modifier,
                        popularMovies: List<Movie>?,
                        topRatedMovies: List<Movie>?,
                        topEarnerMovies: List<Movie>?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(50.dp).align(Alignment.CenterHorizontally)
        )
        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Red))
        MovieRow(popularMovies, "Popular")
        MovieRow(topRatedMovies, "Top Rated")
        MovieRow(topEarnerMovies, "Rating")
    }
}

@Composable
fun MovieRow(movies: List<Movie>?, title: String) {
    Text(title, style = MaterialTheme.typography.headlineSmall)
    movies?.let {
        LazyRow {
            items(it) { movie ->
                MovieItem(movie)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie) {
    Column {
        GlideImage(
            model = "https://image.tmdb.org/t/p/w$ITEM_IMAGE_HEIGHT${movie.poster_path}",
            contentDescription = null,
            modifier = Modifier.height(ITEM_IMAGE_HEIGHT.dp).width((ITEM_IMAGE_WIDTH).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesPreview() {
    MoviesCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MoviesScreenContent(popularMovies = listOf(), topRatedMovies = listOf(), topEarnerMovies = listOf())
        }
    }
}