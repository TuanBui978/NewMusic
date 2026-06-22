package com.example.library.ui

import java.util.UUID

sealed interface LibraryUiAction {
    data object OnStorageClick: LibraryUiAction
    data object OnLayoutTypeChange: LibraryUiAction
}