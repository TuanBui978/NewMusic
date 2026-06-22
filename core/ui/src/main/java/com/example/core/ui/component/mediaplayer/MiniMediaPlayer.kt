package com.example.core.ui.component.mediaplayer

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.material3.buttons.PlayPauseButton
import com.example.core.ui.component.progress.ProgressBar
import com.example.core.ui.component.media.MediaImage
import com.example.core.ui.theme.AppTheme

@OptIn(UnstableApi::class)
@Composable
fun MiniMediaPlayer(
    modifier: Modifier = Modifier,
    player: Player?,
    progress: () -> Float = { 0f },
    image: Any? = null,
    title: String = "",
    subtitle: String? = null,
    color: Color = AppTheme.colorScheme.surface,
    isFavorite: Boolean = false,
    onFavoriteClick: (Boolean) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = AppTheme.shapes.small,
        color = color
    ) {
        Column {
            Row(
                modifier = Modifier.padding(AppTheme.dimensions.paddingSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spaceMedium)
            ) {
                MediaImage(
                    modifier = Modifier.size(AppTheme.dimensions.imageSizeMedium),
                    imageUrl = image
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spaceSmall)
                ) {
                    Text(
                        text = title,
                        style = AppTheme.typography.titleMedium,
                        maxLines = 1,
                    )
                    Text(
                        text = subtitle ?: "",
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
                Row {
                    AnimatedContent(
                        targetState = isFavorite,
                        label = "Favorite"
                    ) { state ->
                        if (state) {
                            IconButton(onClick = { onFavoriteClick(false) }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = AppTheme.colorScheme.error
                                )
                            }
                        } else {
                            IconButton(onClick = { onFavoriteClick(true) }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    PlayPauseButton(
                        player = player,
                        imageVector = {
                            if (this.showPlay) Icons.Default.PlayArrow else Icons.Default.Pause
                        }
                    )
                }
            }

            ProgressBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.paddingMedium),
                progress = { progress().coerceIn(0f, 1f) }
            )
        }
    }
}

@Preview
@Composable
private fun MiniMediaPlayerPreview() {
    AppTheme {
        Box(
            modifier = Modifier.background(Color.White)
        ) {
            MiniMediaPlayer(
                player = null,
                progress = { 0.45f },
                image = "",
                title = "Em Của Ngày Hôm Qua",
                subtitle = "Sơn Tùng M-TP",
                isFavorite = true
            )
        }
    }
}