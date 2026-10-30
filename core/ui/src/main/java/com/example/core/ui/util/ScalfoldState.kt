package com.example.core.ui.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

interface ScaffoldState {
    val showNavigationBar: State<Boolean>
}

@Stable
class BottomOverlayInsets(
    private val calculateBottomPx: Density.() -> Int
) : WindowInsets {
    override fun getLeft(density: Density, layoutDirection: LayoutDirection): Int = 0
    override fun getTop(density: Density): Int = 0
    override fun getRight(density: Density, layoutDirection: LayoutDirection): Int = 0
    override fun getBottom(density: Density): Int = density.calculateBottomPx()
}

val LocalBottomOverlayInsets = staticCompositionLocalOf<WindowInsets> {
    WindowInsets(0, 0, 0, 0)
}
