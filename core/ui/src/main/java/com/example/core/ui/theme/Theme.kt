/**
 * Copyright (c) 2026.
 *
 * Author: TuanBuiQuang
 * Created: 3/7/26
 *
 * Module Owner: Android Core Team
 * Reviewer: Tech Lead
 */
package com.example.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.example.core.ui.util.LocalWindowSizeClass
import com.example.core.ui.util.adaptiveWidthValue

object AppTheme {
    val extraColor
        @Composable
        @ReadOnlyComposable
        get() = LocalExtraColor.current
    val extraGradient
        @Composable
        @ReadOnlyComposable
        get() = LocalExtraGradient.current
    val dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
    val colorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme
    val typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
    val shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes
}

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    supportLargeAndXLargeWidth: Boolean = false,
    content: @Composable () -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth).windowSizeClass
    CompositionLocalProvider(
        LocalWindowSizeClass provides windowSizeClass
    ) {
        val typography = adaptiveWidthValue(
            compact = compactTypography,
            medium = mediumTypography,
            expanded = expandedTypography
        )
        val dimension = adaptiveWidthValue(
            compact = compactDimens,
            medium = mediumDimens,
            expanded = expandedDimens
        )
        val shapes = adaptiveWidthValue(
            compact = compactShapes,
            medium = mediumShapes,
            expanded = expandedShapes
        )
        val colors = if (!useDarkTheme) darkColorScheme else darkColorScheme
        CompositionLocalProvider(
            LocalExtraColor provides extraColor,
            LocalExtraGradient provides extraGradient,
            LocalDimens provides dimension
        ) {
            MaterialTheme(
                colorScheme = colors,
                typography = typography,
                shapes = shapes,
                content = content
            )
        }
    }
}

