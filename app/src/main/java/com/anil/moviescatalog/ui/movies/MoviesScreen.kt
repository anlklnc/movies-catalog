package com.anil.moviescatalog.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anil.moviescatalog.R
import com.anil.moviescatalog.ui.theme.MoviesCatalogTheme

@Composable
fun MoviesScreen(modifier: Modifier = Modifier, vm: MoviesViewModel = viewModel()) {
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
        Text(text = vm.name)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MoviesScreen()
        }
    }
}