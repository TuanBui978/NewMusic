package com.example.albumdetail.ui.standard

import com.example.domain.model.media.Song

data class AlbumDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val title: String = "",
    val subtitle: String? = null,
    val songs: List<Song> = emptyList()
)