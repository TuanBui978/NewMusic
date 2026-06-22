package com.example.playing.ui

sealed interface PlayingUiAction {
    data class OnFavoriteClick(val isFavorite: Boolean) : PlayingUiAction
}