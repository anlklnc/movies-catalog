package com.anil.moviescatalog.ui.streaming

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.anil.moviescatalog.usecase.BuildMediaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StreamingViewModel @Inject constructor(
    val buildMediaItemUseCase: BuildMediaItemUseCase
): ViewModel() {
    var exoPlayer: ExoPlayer? = null
        private set

    fun initializePlayer(context: Context) {
        if (exoPlayer == null) {
            val mediaItem = buildMediaItemUseCase()

            exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null
    }
}