package com.example.albumdetail.ui.component.media

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.ui.component.media.MediaListItem
import com.example.core.ui.theme.AppTheme

@Composable
fun SongListItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    imageUrl: Any? = null,
    isExplicit: Boolean = false,
    onItemClick: () -> Unit,
    onMoreOptionClick: () -> Unit = {}
) {
    MediaListItem(
        modifier = modifier.fillMaxWidth(),
        imageUrl = imageUrl,
        onClick = onItemClick,
        title = {
            Text(
                modifier = Modifier.basicMarquee(),
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        subtitle = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isExplicit) {
                    ExplicitBadge()
                    Spacer(modifier = Modifier.width(AppTheme.dimensions.spaceExtraSmall))
                }

                Text(
                    modifier = Modifier.basicMarquee(),
                    text = artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        trailingItem = {
            IconButton(
                onClick = onMoreOptionClick,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Thêm tùy chọn"
                )
            }
        }
    )
}

@Composable
private fun ExplicitBadge(modifier: Modifier = Modifier) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        color = AppTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        shape = AppTheme.shapes.extraSmall
    ) {
        Text(
            text = "E",
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            color = AppTheme.colorScheme.surface,
            style = AppTheme.typography.labelSmall
        )
    }
}