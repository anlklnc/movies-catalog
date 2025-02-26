package com.anil.moviescatalog.usecase

import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import javax.inject.Inject

// Fixed video and license url's are provided for testing purposes
private const val VIDEO_URL = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
private const val LICENSE_URL = "https://proxy.uat.widevine.com/proxy?video_id=2015_tears&provider=widevine_test"

/**
 * Builds a media item with drm configuration
 */
class BuildMediaItemUseCase @Inject constructor() {
    operator fun invoke(): MediaItem {
        return MediaItem.Builder()
            .setUri(Uri.parse(VIDEO_URL))
            .setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .setLicenseUri(LICENSE_URL)
                    .build()
            )
            .build()
    }
}