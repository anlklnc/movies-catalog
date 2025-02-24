package com.anil.moviescatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.anil.moviescatalog.model.Movie
import com.anil.moviescatalog.ui.CustomNavType.MovieNavType
import com.anil.moviescatalog.ui.Navigation
import com.anil.moviescatalog.ui.movieDetails.MovieDetailsScreen
import com.anil.moviescatalog.ui.movies.MoviesScreen
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesCatalogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController,
                        startDestination = Navigation.MoviesRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Navigation.MoviesRoute> {
                            MoviesScreen(
                                onNavigateToDetails = { movie ->
                                    navController.navigate(Navigation.MovieDetailsRoute(movie))
                                }
                            )
                        }
                        composable<Navigation.MovieDetailsRoute>(
                            typeMap = mapOf(typeOf<Movie>() to MovieNavType)
                        ){
                            val args = it.toRoute<Navigation.MovieDetailsRoute>()
                            MovieDetailsScreen(
                                args.movie
                            )
                        }
                    }
                }
            }
        }
    }
}