package com.example.albumdetail.ui.local

import com.example.domain.model.media.Song

sealed class LocalAlbumDetailUiAction {
    data object OnBack : LocalAlbumDetailUiAction()
    data object OnPermissionAllow : LocalAlbumDetailUiAction()
    data class OnSongClick(val song: Song) : LocalAlbumDetailUiAction()
}