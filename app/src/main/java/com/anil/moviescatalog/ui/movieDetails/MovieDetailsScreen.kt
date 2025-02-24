package com.anil.moviescatalog.ui.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anil.moviescatalog.BuildConfig
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme
import com.anil.moviescatalog.util.NumberUtil
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

const val ITEM_IMAGE_HEIGHT = 400

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsScreen (
    movie: Movie
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(Color.Black)) {
            GlideImage(
                model = BuildConfig.IMAGE_URL + ITEM_IMAGE_HEIGHT + movie.poster_path,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize().align(Alignment.Center)
            )
            Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating Icon",
                        tint = Color.Yellow
                    )
                    Text(
                        text = NumberUtil.round(movie.vote_average).toString(),
                        color = Color.White,
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Text(movie.release_date, color = Color.White)
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Rating Icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).weight(1.0f),
            text = movie.overview
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesPreview() {
    MoviesCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MovieDetailsScreen(
                movie = Movie(
                    id = 1,
                    title = "Movie Title",
                    overview = "Movie Overview",
                    poster_path = "Movie Poster Path",
                    release_date = "1/10/2013",
                    vote_average = 5.0,
                    video = true,
                    adult = false
                )
            )
        }
    }
}