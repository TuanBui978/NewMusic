package com.example.albumdetail.ui.local

import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.core.base.BaseViewModel
import com.example.core.media.manager.MediaControllerManager
import com.example.domain.model.media.Song
import com.example.domain.model.result.DataError
import com.example.domain.model.result.Result
import com.example.domain.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.core.net.toUri

@HiltViewModel
class LocalAlbumDetailViewModel @Inject constructor(
    mediaControllerManager: MediaControllerManager,
    private val musicRepository: MusicRepository,
) : BaseViewModel<LocalAlbumDetailUiState, LocalAlbumDetailUiAction, LocalAlbumDetailUiEvent>(
    LocalAlbumDetailUiState()
) {

    private val player = mediaControllerManager.player

    init {
        fetchLocalMusic()
    }

    override fun handleAction(action: LocalAlbumDetailUiAction) {
        when (action) {
            LocalAlbumDetailUiAction.OnBack -> {}
            LocalAlbumDetailUiAction.OnPermissionAllow -> fetchLocalMusic()
            is LocalAlbumDetailUiAction.OnSongClick -> playSong(action.song)
        }
    }

    private fun playSong(song: Song) {
        player.value?.apply {
            val mediaItem = MediaItem.Builder()
                .setMediaId(song.id)
                .setUri(song.path)
                .build()
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun fetchLocalMusic() {
        musicRepository.fetchLocalMusic().onEach { result ->
            when (result) {
                is Result.Loading -> {
                    updateState { copy(isLoading = true) }
                }
                is Result.Success-> {
                    updateState {
                        copy(
                            isLoading = false,
                            songs = result.data
                        )
                    }
                }
                is Result.Error -> {
                    if (result.error is DataError.Local.PermissionDenied) {
                        sendEvent(
                            LocalAlbumDetailUiEvent.RequestPermission
                        )
                    }
                    else {
                        updateState {
                            copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}