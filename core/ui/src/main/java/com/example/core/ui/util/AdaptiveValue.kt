package com.example.core.ui.util

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.core.layout.WindowSizeClass

internal val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass?> {
    null
}

fun <T> adaptiveWidthValue(
    compact: T,
    medium: T? = null,
    expanded: T? = null,
    large: T? = null,
    extraLarge: T? = null,
    windowSizeClass: WindowSizeClass
): T {
    return when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXTRA_LARGE_LOWER_BOUND) ->
            extraLarge ?: large ?: expanded ?: medium ?: compact

        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_LARGE_LOWER_BOUND) ->
            large ?: expanded ?: medium ?: compact

        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ->
            expanded ?: medium ?: compact

        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
            medium ?: compact

        else -> compact
    }
}

@Composable
fun<T> adaptiveWidthValue(
    compact: T,
    medium: T? = null,
    expanded: T? = null,
    large: T? = null,
    extraLarge: T? = null,
): T {
    val windowSizeClass = LocalWindowSizeClass.current ?: currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
    return adaptiveWidthValue(compact, medium, expanded, large, extraLarge, windowSizeClass)
}

fun <T> adaptiveHeightValue(
    compact: T,
    medium: T? = null,
    expanded: T? = null,
    windowSizeClass: WindowSizeClass
): T {
    return when {
        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND) ->
            expanded ?: medium ?: compact

        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) ->
            medium ?: compact

        else -> compact
    }
}

@Composable
fun <T> adaptiveHeightValue(
    compact: T,
    medium: T? = null,
    expanded: T? = null
): T {
    val windowSizeClass = LocalWindowSizeClass.current ?: currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
    return adaptiveHeightValue(compact, medium, expanded, windowSizeClass)
}