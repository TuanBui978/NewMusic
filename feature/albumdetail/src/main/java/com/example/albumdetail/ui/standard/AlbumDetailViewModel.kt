package com.example.albumdetail.ui.standard

import androidx.lifecycle.SavedStateHandle
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<AlbumDetailUiState, AlbumDetailUiAction, AlbumDetailUiEvent>(
    AlbumDetailUiState()
) {
    override fun handleAction(action: AlbumDetailUiAction) {

    }
}