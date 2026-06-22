package com.example.core.ui.component.media

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.core.ui.theme.AppTheme

@Immutable
data class MediaItemStyle(
    val imageSize: Dp,
    val imageShape: Shape,
    val titleStyle: TextStyle,
    val subtitleStyle: TextStyle,
    val contentPadding: PaddingValues,
    val horizontalSpacing: Dp
)

@Immutable
data class MediaItemColors(
    val titleColor: Color,
    val subtitleColor: Color,
    val trailingContentColor: Color
)

object MediaItemDefaults {

    @Composable
    fun colors(
        titleColor: Color = AppTheme.colorScheme.onSurface,
        subtitleColor: Color = AppTheme.colorScheme.onSurfaceVariant,
        trailingContentColor: Color = AppTheme.colorScheme.onSurfaceVariant
    ): MediaItemColors = MediaItemColors(
        titleColor = titleColor,
        subtitleColor = subtitleColor,
        trailingContentColor = trailingContentColor
    )

    @Composable
    fun large(
        imageShape: Shape = AppTheme.shapes.large
    ) = MediaItemStyle(
        imageSize = AppTheme.dimensions.imageSizeLarge,
        imageShape = imageShape,
        titleStyle = AppTheme.typography.titleLarge,
        subtitleStyle = AppTheme.typography.titleMedium,
        contentPadding = PaddingValues(
            horizontal = AppTheme.dimensions.paddingMedium,
            vertical = AppTheme.dimensions.paddingMedium
        ),
        horizontalSpacing = AppTheme.dimensions.spaceMedium
    )

    @Composable
    fun medium(
        imageShape: Shape = AppTheme.shapes.medium
    ) = MediaItemStyle(
        imageSize = AppTheme.dimensions.imageSizeMedium,
        imageShape = imageShape,
        titleStyle = AppTheme.typography.titleMedium,
        subtitleStyle = AppTheme.typography.bodyMedium,

        contentPadding = PaddingValues(
            horizontal = AppTheme.dimensions.paddingMedium,
            vertical = AppTheme.dimensions.paddingSmall
        ),
        horizontalSpacing = AppTheme.dimensions.spaceMedium
    )

    @Composable
    fun small(
        imageShape: Shape = AppTheme.shapes.small
    ) = MediaItemStyle(
        imageSize = AppTheme.dimensions.iconLarge,
        imageShape = imageShape,
        titleStyle = AppTheme.typography.labelLarge,
        subtitleStyle = AppTheme.typography.labelSmall,

        contentPadding = PaddingValues(
            horizontal = AppTheme.dimensions.paddingMedium,
            vertical = AppTheme.dimensions.paddingSmall
        ),
        horizontalSpacing = AppTheme.dimensions.spaceSmall
    )
}
