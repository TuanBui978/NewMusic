package com.example.core.media.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import com.example.core.media.model.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
data class PlayBackState(
    val player: Player?,
    val isPlaying: Boolean,
    val currentMedia: Media?,
    val currentPosition: () -> Long
)

interface PlayBackStateManager {
    val player: StateFlow<Player?>
    val isPlaying: StateFlow<Boolean>
    val currentPosition: Flow<Long>
    val currentMedia: StateFlow<Media?>
    fun release()
}

class PlayBackStateManagerImpl(
    private val mediaControllerManager: MediaControllerManager,
) : PlayBackStateManager {

    override val player = mediaControllerManager.player

    private val _playing = MutableStateFlow(false)
    override val isPlaying = _playing.asStateFlow()

    private val _currentMedia = MutableStateFlow<Media?>(null)
    override val currentMedia = _currentMedia.asStateFlow()

    private val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _playing.update { isPlaying }
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)

            _currentMedia.update {
                Media(
                    title = mediaMetadata.title?.toString() ?: "Unknown",
                    artist = mediaMetadata.artist?.toString() ?: "Unknown",
                    albumArtUri = mediaMetadata.artworkData ?: mediaMetadata.artworkUri?.toString(),
                    trackUri = player.value?.currentMediaItem?.localConfiguration?.uri?.toString() ?: "",
                    duration = player.value?.duration?.coerceAtLeast(0L) ?: 0L
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentPosition: Flow<Long> = _playing.flatMapLatest { isPlaying ->
        flow {
            if (isPlaying) {
                while (true) {
                    emit(player.value?.currentPosition ?: 0L)
                    delay(1000L)
                }
            } else {
                emit(player.value?.currentPosition ?: 0L)
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.Main).launch {
            mediaControllerManager.player.collect { currentPlayer ->
                currentPlayer?.addListener(listener)
            }
        }
    }

    override fun release() {
        player.value?.removeListener(listener)
    }
}

@Composable
fun rememberPlayBackState(
    stateManager: PlayBackStateManager
) : PlayBackState {
    val isPlaying by stateManager.isPlaying.collectAsStateWithLifecycle()
    val media by stateManager.currentMedia.collectAsStateWithLifecycle()
    val positionState = stateManager.currentPosition.collectAsStateWithLifecycle(0L)
    val player by stateManager.player.collectAsStateWithLifecycle()
    return remember(isPlaying, media, stateManager.player) {
        PlayBackState(
            player = player,
            isPlaying = isPlaying,
            currentMedia = media,
            currentPosition = { positionState.value }
        )
    }
}