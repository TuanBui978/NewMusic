package com.example.albumdetail.ui.local

import com.example.domain.model.media.Song
import com.example.domain.model.result.DataError

data class LocalAlbumDetailUiState(
    val isLoading: Boolean = false,
    val error: DataError? = null,
    val title: String = "",
    val subtitle: String? = null,
    val songs: List<Song> = emptyList()
)