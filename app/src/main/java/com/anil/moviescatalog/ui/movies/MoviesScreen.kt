package com.anil.moviescatalog.ui.movies

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.anil.moviescatalog.BuildConfig
import com.anil.moviescatalog.R
import com.anil.moviescatalog.model.Category
import com.anil.moviescatalog.model.Movie
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

const val ITEM_IMAGE_HEIGHT = 200
const val ITEM_IMAGE_WIDTH = 134

@Composable
fun MoviesScreen(modifier: Modifier = Modifier, vm: MoviesViewModel = viewModel()) {
    val popularMovies = vm.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = vm.topRatedMovies.collectAsLazyPagingItems()
    val topEarnerMovies = vm.topEarnerMovies.collectAsLazyPagingItems()
    MoviesScreenContent(modifier,
        popularMovies,
        topRatedMovies,
        topEarnerMovies)
}

@Composable
fun MoviesScreenContent(modifier: Modifier = Modifier,
                        popularMovies: LazyPagingItems<Movie>,
                        topRatedMovies: LazyPagingItems<Movie>,
                        topEarnerMovies: LazyPagingItems<Movie>
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
        MovieRow(popularMovies, Category.POPULAR)
        MovieRow(topRatedMovies, Category.TOP_RATED)
        MovieRow(topEarnerMovies, Category.REVENUE)
    }
}

@Composable
fun MovieRow(movies: LazyPagingItems<Movie>,category: Category) {
    val title = when (category) {
        Category.POPULAR -> "Popular"
        Category.TOP_RATED -> "Top Rated"
        Category.REVENUE -> "Revenue"
    }
    Text(title, style = MaterialTheme.typography.headlineSmall)
    LazyRow {
        itemsIndexed(movies) { index, movie ->
            movie?.let {
                MovieItem(movie){
                    Log.i("!!!", "MovieRow: $category + $index")
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie, onClick: ()->Unit) {
    Column(modifier = Modifier.padding(horizontal = 3.dp).clickable{onClick()}) {
        GlideImage(
            model = BuildConfig.IMAGE_URL + ITEM_IMAGE_HEIGHT + movie.poster_path,
            contentDescription = null,
            loading = placeholder(R.drawable.ic_ondemand_video_24),
            modifier = Modifier.height(ITEM_IMAGE_HEIGHT.dp).width((ITEM_IMAGE_WIDTH).dp).background(Color.Black)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MoviesPreview() {
//    MoviesCatalogTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            MoviesScreenContent(popularMovies = listOf(), topRatedMovies = listOf(), topEarnerMovies = listOf())
//        }
//    }
//}