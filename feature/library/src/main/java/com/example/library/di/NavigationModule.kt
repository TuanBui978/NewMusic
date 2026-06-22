package com.example.library.di

import com.example.core.navigation.GraphRegister
import com.example.library.navigation.LibraryGraphRegister
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
    abstract fun bindLibraryGraphRegister(
        libraryGraphRegister: LibraryGraphRegister
    ): GraphRegister
}