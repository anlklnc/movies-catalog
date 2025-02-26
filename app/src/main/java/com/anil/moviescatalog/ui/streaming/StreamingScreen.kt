package com.anil.moviescatalog.ui.streaming

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.anil.moviescatalog.model.Movie

@Composable
fun StreamingScreen (
    movie: Movie,
    vm: StreamingViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    vm.initializePlayer(LocalContext.current)

    if (isLandscape) {
        Box(modifier = Modifier.fillMaxSize()) {
            ExoPlayerView(vm.exoPlayer, true)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            ExoPlayerView(vm.exoPlayer)
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = movie.overview,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ExoPlayerView(exoPlayer: ExoPlayer?, isLandscape: Boolean = false) {
    val context = LocalContext.current
    LifecycleStartEffect(Unit) {
        if(exoPlayer?.isPlaying == false) {
            exoPlayer.play()
        }
        onStopOrDispose {
            if (exoPlayer?.isPlaying == true) {
                exoPlayer.pause()
            }
        }
    }
    AndroidView(
        factory = {
            PlayerView(context).apply {
                this.player = exoPlayer
            }
        },
        modifier = Modifier
            .conditional(isLandscape){fillMaxSize()}
            .background(Color.Black)
    )
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}
