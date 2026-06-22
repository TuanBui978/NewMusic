package com.example.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import com.example.core.ui.theme.AppTheme

class AppThemePreviewWrapper : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable (() -> Unit)) {
        AppTheme {
            content()
        }
    }
}