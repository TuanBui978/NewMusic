package com.example.data.datasource

import com.example.data.util.LocalMediaQuery
import com.example.domain.datasource.LocalMusicDataSource
import com.example.domain.model.media.Song
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class LocalMusicDataSourceImpl @Inject constructor(
    val localMediaQuery: LocalMediaQuery,
) : LocalMusicDataSource {
    override suspend fun fetchLocalMusic(): List<Song> {
        return localMediaQuery.fetchAllSongs()
    }
}