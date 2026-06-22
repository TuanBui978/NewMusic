package com.example.data.di

import com.example.data.repository.MusicRepositoryImpl
import com.example.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMusicRepository(musicRepositoryImpl: MusicRepositoryImpl): MusicRepository
}