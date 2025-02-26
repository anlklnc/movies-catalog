package com.anil.moviescatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.CustomNavType.MovieNavType
import com.anil.moviescatalog.ui.MoviesAndDetailsScreen
import com.anil.moviescatalog.ui.Navigation
import com.anil.moviescatalog.ui.movieDetails.MovieDetailsScreen
import com.anil.moviescatalog.ui.movies.MoviesScreen
import com.anil.moviescatalog.ui.streaming.StreamingScreen
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

const val TABLET_WIDTH_THRESHOLD = 600

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesCatalogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val configuration = LocalConfiguration.current
                    val tabletLayout = configuration.screenWidthDp >= TABLET_WIDTH_THRESHOLD
                    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

                    NavHost(
                        navController = navController,
                        startDestination = if(tabletLayout) Navigation.MoviesAndDetailsRoute else Navigation.MoviesRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        if (tabletLayout) {
                            // Tablet Layout
                            composable<Navigation.MoviesAndDetailsRoute> {
                                MoviesAndDetailsScreen(
                                    selectedMovie = selectedMovie,
                                    onMovieSelected = { movie ->
                                        selectedMovie = movie
                                    },
                                    onNavigateToStreaming = { movie ->
                                        navController.navigate(Navigation.StreamingRoute(movie))
                                    }
                                )
                            }
                        } else {
                            // Phone Layout
                            composable<Navigation.MoviesRoute> {
                                MoviesScreen(
                                    Modifier.fillMaxSize(),
                                    onNavigateToDetails = { movie ->
                                        navController.navigate(Navigation.MovieDetailsRoute(movie))
                                    }
                                )
                            }
                            composable<Navigation.MovieDetailsRoute>(
                                typeMap = mapOf(typeOf<Movie>() to MovieNavType)
                            ) {
                                val args = it.toRoute<Navigation.MovieDetailsRoute>()
                                MovieDetailsScreen(
                                    args.movie,
                                    Modifier.fillMaxSize(),
                                    onNavigateToStreaming = {
                                        navController.navigate(Navigation.StreamingRoute(args.movie))
                                    }
                                )
                            }
                        }
                        composable<Navigation.StreamingRoute>(
                            typeMap = mapOf(typeOf<Movie>() to MovieNavType)
                        ) {
                            val args = it.toRoute<Navigation.StreamingRoute>()
                            StreamingScreen(
                                args.movie
                            )
                        }
                    }
                }
            }
        }
    }
}