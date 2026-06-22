package com.example.music

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.example.utils.coil.AudioCoverFetcher
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
class MusicApplication : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader
            .Builder(context)
            .components {
                add(AudioCoverFetcher.Factory(context))
            }
            .build()
    }

}