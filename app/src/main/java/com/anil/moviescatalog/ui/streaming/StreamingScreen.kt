package com.anil.moviescatalog.ui.streaming

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.anil.moviescatalog.model.Movie

private const val VIDEO_URL = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
private const val LICENSE_URL = "https://proxy.uat.widevine.com/proxy?video_id=2015_tears&provider=widevine_test"

@Composable
fun StreamingScreen (
    movie: Movie
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ExoPlayerView(VIDEO_URL, LICENSE_URL)
        Text(
            text = movie.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(text = movie.overview,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ExoPlayerView(uri: String, licenseUrl: String) {
    val context = LocalContext.current

    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    DisposableEffect(context) {
        val uuid = C.WIDEVINE_UUID

        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse(uri))
            .setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(uuid)
                    .setLicenseUri(licenseUrl)
                    .build()
            )
            .build()

        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
        player = exoPlayer

        onDispose {
            exoPlayer.release()
        }
    }

    LifecycleStartEffect(Unit) {
        if(player?.isPlaying == false) {
            Log.i("!!!", "ExoPlayerView: play")
            player?.play()
        }
        onStopOrDispose {
            if (player?.isPlaying == true) {
                Log.i("!!!", "ExoPlayerView: stop")
                player?.stop()
            }
        }
    }

    AndroidView(
        factory = {
            PlayerView(context)
        },
        update = {
            it.player = player
        }
    )
}
