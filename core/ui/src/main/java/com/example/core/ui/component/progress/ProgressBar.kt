package com.example.core.ui.component.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme

data class ProgressBarColors(
    val trackColor: Color,
    val activeColor: Color,
)

object ProgressBarDefault {
    @Composable
    fun progressBarColors(
        trackColor: Color = AppTheme.colorScheme.outline,
        activeColor: Color = AppTheme.colorScheme.primary,
    ) = ProgressBarColors(
        trackColor = trackColor,
        activeColor = activeColor,
    )
}

@Composable
fun ProgressBar(
    modifier: Modifier,
    progress: () -> Float,
    colors: ProgressBarColors = ProgressBarDefault.progressBarColors(),
) {
    Canvas(
        modifier = modifier
            .height(2.dp)
    ) {
        val progress = progress()
        val cornerRadius = CornerRadius(size.height / 2f, size.height / 2f)
        drawRoundRect(
            color = colors.trackColor,
            size = size,
            cornerRadius = CornerRadius(0f, size.height / 2f)
        )
        if (progress > 0f) {
            drawRoundRect(
                color = colors.activeColor,
                size = size.copy(width = size.width * progress),
                cornerRadius = cornerRadius
            )
        }
    }
}