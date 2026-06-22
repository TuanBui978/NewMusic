package com.example.core.ui.component.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.core.ui.component.layout.EntityRowLayout
import com.example.core.ui.theme.AppTheme

@Composable
fun MediaListItem(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    trailingItem: (@Composable () -> Unit)? = null,
    imageUrl: Any? = null,
    shape: Shape = AppTheme.shapes.medium,
    style: MediaItemStyle = MediaItemDefaults.medium(),
    colors: MediaItemColors = MediaItemDefaults.colors(),
    layoutHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(style.horizontalSpacing),
    layoutVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    titleHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    titleVerticalArrangement: Arrangement.Vertical = Arrangement.Top,
    onClick: () -> Unit = {}
) {
    EntityRowLayout(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = style.contentPadding,
        layoutHorizontalArrangement = layoutHorizontalArrangement,
        layoutVerticalAlignment = layoutVerticalAlignment,
        titleHorizontalAlignment = titleHorizontalAlignment,
        titleVerticalArrangement = titleVerticalArrangement,
        leadingItem = {
            val painter = rememberAsyncImagePainter(model = imageUrl)
            val iconPainter = rememberVectorPainter(image = Icons.Rounded.MusicNote)
            val iconTint = AppTheme.colorScheme.onSurfaceVariant
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(style.imageSize)
                    .clip(style.imageShape)
                    .background(AppTheme.colorScheme.surfaceVariant)
                    .drawWithContent {
                        if (painter.state.value !is AsyncImagePainter.State.Success) {
                            val iconSize = size.minDimension / 2f
                            val leftOffset = (size.width - iconSize) / 2f
                            val topOffset = (size.height - iconSize) / 2f
                            translate(left = leftOffset, top = topOffset) {
                                with(iconPainter) {
                                    draw(
                                        size = Size(iconSize, iconSize),
                                        alpha = 0.5f,
                                        colorFilter = ColorFilter.tint(iconTint)
                                    )
                                }
                            }
                        }
                        drawContent()
                    },
                contentScale = ContentScale.Crop
            )
        },

        title = {
            CompositionLocalProvider(LocalContentColor provides colors.titleColor) {
                ProvideTextStyle(style.titleStyle) {
                    title()
                }
            }
        },

        subtitle = if (subtitle != null) {
            {
                CompositionLocalProvider(LocalContentColor provides colors.subtitleColor) {
                    ProvideTextStyle(style.subtitleStyle) {
                        subtitle()
                    }
                }
            }
        } else null,

        trailingItem = if (trailingItem != null) {
            {
                CompositionLocalProvider(LocalContentColor provides colors.trailingContentColor) {
                    trailingItem()
                }
            }
        } else null
    )
}
