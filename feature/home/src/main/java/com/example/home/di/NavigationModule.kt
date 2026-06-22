package com.example.home.di

import com.example.core.navigation.GraphRegister
import com.example.home.navigation.HomeGraphRegister
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
    abstract fun bindsHomeGraphRegister(impl: HomeGraphRegister): GraphRegister
}