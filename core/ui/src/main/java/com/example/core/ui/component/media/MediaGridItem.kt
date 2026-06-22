package com.example.core.ui.component.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.core.ui.component.layout.EntityGridLayout
import com.example.core.ui.theme.AppTheme

@Composable
fun MediaGridItem(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    topSubtitle: (@Composable () -> Unit)? = null,
    bottomSubtitle: (@Composable () -> Unit)? = null,
    trailingItem: (@Composable () -> Unit)? = null,
    imageUrl: String? = null,
    style: MediaItemStyle = MediaItemDefaults.medium(),
    colors: MediaItemColors = MediaItemDefaults.colors(),
    layoutHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    layoutVerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(style.horizontalSpacing),
    titleHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    titleVerticalArrangement: Arrangement.Vertical = Arrangement.Top,
    onClick: () -> Unit = {}
) {
    EntityGridLayout(
        modifier = modifier,
        onClick = onClick,
        contentPadding = style.contentPadding,
        layoutHorizontalAlignment = layoutHorizontalAlignment,
        layoutVerticalArrangement = layoutVerticalArrangement,
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
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(style.imageShape)
                    .background(AppTheme.colorScheme.surfaceVariant)
                    .drawWithContent {
                        if (painter.state.value !is AsyncImagePainter.State.Success) {
                            val iconSize = size.minDimension / 3f
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

        // Xử lý topSubtitle
        topSubtitle = if (topSubtitle != null) {
            {
                CompositionLocalProvider(LocalContentColor provides colors.subtitleColor) {
                    ProvideTextStyle(style.subtitleStyle) {
                        topSubtitle()
                    }
                }
            }
        } else null,

        // Xử lý title
        title = {
            CompositionLocalProvider(LocalContentColor provides colors.titleColor) {
                ProvideTextStyle(style.titleStyle) {
                    title()
                }
            }
        },

        // Xử lý bottomSubtitle
        bottomSubtitle = if (bottomSubtitle != null) {
            {
                CompositionLocalProvider(LocalContentColor provides colors.subtitleColor) {
                    ProvideTextStyle(style.subtitleStyle) {
                        bottomSubtitle()
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