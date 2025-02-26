package com.anil.moviescatalog.ui.movies

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.anil.moviescatalog.BuildConfig
import com.anil.moviescatalog.R
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.RedLineSeperator
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val ITEM_IMAGE_HEIGHT = 200
const val ITEM_IMAGE_WIDTH = 134

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Movie) -> Unit,
    vm: MoviesViewModel = hiltViewModel()) {

    val popularMovies = vm.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = vm.topRatedMovies.collectAsLazyPagingItems()
    val topEarnerMovies = vm.topEarnerMovies.collectAsLazyPagingItems()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            vm.error.collectLatest { exception ->
                Toast.makeText(context, "Network error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    MoviesScreenContent(
        onNavigateToDetails,
        popularMovies,
        topRatedMovies,
        topEarnerMovies,
        modifier)
}

@Composable
fun MoviesScreenContent(
    onClick: (Movie) -> Unit,
    popularMovies: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    topEarnerMovies: LazyPagingItems<Movie>,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_movie),
            contentDescription = null,
            modifier = Modifier.size(50.dp).align(Alignment.CenterHorizontally).padding(top = 16.dp)
        )
        RedLineSeperator(3)
        MovieRow(popularMovies, Category.POPULAR, onClick)
        MovieRow(topRatedMovies, Category.TOP_RATED, onClick)
        MovieRow(topEarnerMovies, Category.REVENUE, onClick)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun MovieRow(movies: LazyPagingItems<Movie>, category: Category, onClick: (Movie) -> Unit) {
    val title = when (category) {
        Category.POPULAR -> "Popular"
        Category.TOP_RATED -> "Top Rated"
        Category.REVENUE -> "Revenue"
    }
    Text(title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier.padding(8.dp)
    )
    LazyRow {
        items(movies) { movie ->
            movie?.let {
                MovieItem(movie){
                    onClick(movie)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie, onClick: ()->Unit) {
    Column(modifier = Modifier.padding(horizontal = 3.dp).clickable{onClick()}) {
        movie.poster_path?.let { path ->
            GlideImage(
                model = BuildConfig.IMAGE_URL + ITEM_IMAGE_HEIGHT + path,
                contentDescription = null,
                loading = placeholder(R.drawable.ic_movie_24),
                requestBuilderTransform = { it.diskCacheStrategy(DiskCacheStrategy.ALL) },
                modifier = Modifier.height(ITEM_IMAGE_HEIGHT.dp).width((ITEM_IMAGE_WIDTH).dp).background(Color.Black)
            )
        }
    }
}