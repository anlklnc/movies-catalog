package com.anil.moviescatalog.ui.streaming

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = (context as? Activity)?.window
        // screen always on
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // hide system UI in landscape mode
        if (isLandscape) {
            displaySystemUi(context, false)
        } else {
            displaySystemUi(context, true)
        }

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            displaySystemUi(context, true)
        }
    }

    vm.initializePlayer(context)

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

@Suppress("DEPRECATION")
fun displaySystemUi(context: Context, show: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = (context as? Activity)?.window?.insetsController
        if (show) {
            controller?.show(WindowInsets.Type.systemBars())
        } else {
            controller?.hide(WindowInsets.Type.systemBars())
        }
    } else {
        val visibility = if (show) {
            View.SYSTEM_UI_FLAG_VISIBLE
        } else {
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        (context as? Activity)?.window?.decorView?.systemUiVisibility = visibility
    }
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}
