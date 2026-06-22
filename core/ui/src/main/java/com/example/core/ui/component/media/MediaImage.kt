package com.example.core.ui.component.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.runtime.Composable
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
import com.example.core.ui.theme.AppTheme

@Composable
fun MediaImage(
    modifier: Modifier = Modifier,
    imageUrl: Any? = null,
    shape: Shape = AppTheme.shapes.medium,
) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val iconPainter = rememberVectorPainter(image = Icons.Rounded.MusicNote)
    val iconTint = AppTheme.colorScheme.onSurfaceVariant
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .clip(shape)
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
}