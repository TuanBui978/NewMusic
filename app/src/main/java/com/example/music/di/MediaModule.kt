package com.example.music.di

import android.content.Context
import com.example.core.media.manager.MediaControllerManager
import com.example.core.media.manager.PlayBackStateManager
import com.example.core.media.manager.PlayBackStateManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MediaModule {
    @Provides
    fun provideMediaControllerManager(@ApplicationContext context: Context) : MediaControllerManager {
        return MediaControllerManager(context)
    }

    @Provides
    fun providePlaybackStateManager(mediaControllerManager: MediaControllerManager) : PlayBackStateManager {
        return PlayBackStateManagerImpl(mediaControllerManager)
    }
}