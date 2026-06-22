package com.example.library.ui

sealed interface LibraryUiEvent {
    data object NavigateToStorage: LibraryUiEvent
}