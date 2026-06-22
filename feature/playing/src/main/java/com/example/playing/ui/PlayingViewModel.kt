package com.example.playing.ui

import com.example.core.base.BaseViewModel
import com.example.core.media.manager.MediaControllerManager
import com.example.core.media.manager.PlayBackStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class PlayingViewModel @Inject constructor(
    val playerStateManager: PlayBackStateManager
): BaseViewModel<PlayingUiState, PlayingUiAction, PlayingUiEvent>(
    PlayingUiState()
) {
    override fun handleAction(action: PlayingUiAction) {
        when (action) {
            is PlayingUiAction.OnFavoriteClick -> {
                updateState {
                    copy(isFavorite = action.isFavorite)
                }
            }
        }
    }
}