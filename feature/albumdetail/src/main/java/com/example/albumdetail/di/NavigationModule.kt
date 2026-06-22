package com.example.albumdetail.di

import com.example.albumdetail.navigation.AlbumDetailGraphRegister
import com.example.core.navigation.CommonDestinationRegister
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @IntoSet
    @Binds
    abstract fun bindAlbumDetailGraphRegister(
        albumDetailGraphRegister: AlbumDetailGraphRegister
    ): CommonDestinationRegister
}