package com.example.playing.ui

data class PlayingUiState(
    val image: String? = null,
    val title: String = "",
    val subtitle: String? = null,
    val isFavorite: Boolean = false
)