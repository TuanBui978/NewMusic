package com.example.splash.di

import com.example.core.navigation.GraphRegister
import com.example.splash.navigation.SplashGraphRegister
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @IntoSet
    abstract fun bindSplashGraphRegister(impl: SplashGraphRegister): GraphRegister
}