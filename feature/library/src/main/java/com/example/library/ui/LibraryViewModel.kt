package com.example.library.ui

import androidx.lifecycle.ViewModel
import com.example.core.base.BaseViewModel
import com.example.library.ui.component.LayoutType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : BaseViewModel<LibraryUiState, LibraryUiAction, LibraryUiEvent>(
    initialState = LibraryUiState()
) {
    override fun handleAction(action: LibraryUiAction) {
        when (action) {
            is LibraryUiAction.OnStorageClick -> {}
            LibraryUiAction.OnLayoutTypeChange -> onLayoutTypeChange()
        }
    }
    fun onLayoutTypeChange() {
        updateState { copy(layoutType = !layoutType) }
    }
}