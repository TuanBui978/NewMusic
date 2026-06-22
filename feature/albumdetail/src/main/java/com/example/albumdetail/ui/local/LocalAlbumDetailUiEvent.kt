package com.example.albumdetail.ui.local

sealed interface LocalAlbumDetailUiEvent {
    data object RequestPermission : LocalAlbumDetailUiEvent
}