package com.example.data.repository

import com.example.data.mapper.toLocalError
import com.example.domain.datasource.LocalMusicDataSource
import com.example.domain.model.media.Song
import com.example.domain.model.result.DataError
import com.example.domain.model.result.Result
import com.example.domain.repository.MusicRepository
import com.example.utils.corountines.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepositoryImpl @Inject constructor(
    private val localMusicDataSourceImpl: LocalMusicDataSource
) : MusicRepository {
    override fun fetchLocalMusic(): Flow<Result<List<Song>, DataError.Local>> = flow {
        suspendRunCatching {
            emit(Result.Loading)
            localMusicDataSourceImpl.fetchLocalMusic()
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Error(it.toLocalError()))
        }
    }
}