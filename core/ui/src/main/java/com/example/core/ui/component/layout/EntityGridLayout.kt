package com.example.core.ui.component.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme

@Composable
fun EntityGridLayout(
    modifier: Modifier = Modifier,
    leadingItem: (@Composable () -> Unit)? = null,
    topSubtitle: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    bottomSubtitle: (@Composable () -> Unit)? = null,
    trailingItem: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues.Zero,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    layoutHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    layoutVerticalArrangement: Arrangement.Vertical = Arrangement.Top,
    titleHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    titleVerticalArrangement: Arrangement.Vertical = Arrangement.Top,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = layoutHorizontalAlignment,
            verticalArrangement = layoutVerticalArrangement
        ) {
            if (leadingItem != null) {
                leadingItem()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                horizontalAlignment = titleHorizontalAlignment,
                verticalArrangement = titleVerticalArrangement
            ) {
                if (topSubtitle != null) {
                    topSubtitle()
                }
                title()
                if (bottomSubtitle != null) {
                    bottomSubtitle()
                }
            }
            if (trailingItem != null) {
                trailingItem()
            }
        }
    }
}