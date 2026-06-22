package com.example.domain.datasource

import com.example.domain.model.media.Album
import com.example.domain.model.media.Song
import com.example.domain.model.result.DataError
import com.example.domain.model.result.Result

interface LocalMusicDataSource {
    suspend fun fetchLocalMusic(): List<Song>
}

interface RemoteMusicDataSource {
    suspend fun fetchMusic(): Result<Album, DataError.Network>
}