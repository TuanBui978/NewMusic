package com.example.library.ui

import androidx.compose.runtime.Stable
import com.example.domain.model.media.Album
import com.example.domain.model.result.DataError
import com.example.library.ui.component.LayoutType

@Stable
data class LibraryUiState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
    val layoutType: LayoutType = LayoutType.List
)