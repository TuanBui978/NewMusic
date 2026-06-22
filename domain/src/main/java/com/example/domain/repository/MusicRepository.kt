package com.example.domain.repository

import com.example.domain.model.media.Album
import com.example.domain.model.media.Song
import com.example.domain.model.result.DataError
import com.example.domain.model.result.Result
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun fetchLocalMusic() : Flow<Result<List<Song>, DataError.Local>>
}