package com.example.library.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.model.media.Album
import com.example.domain.model.result.DataError
import com.example.core.ui.component.media.MediaGridItem
import com.example.core.ui.component.media.MediaListItem
import com.example.core.ui.theme.AppTheme
import java.util.UUID

enum class LayoutType {
    Grid,
    List;
    operator fun not() = if (this == Grid) List else Grid
}

@Stable
data class AlbumLazyLayoutState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
    val layoutType: LayoutType = LayoutType.List
)

sealed interface AlbumLazyLayoutAction {
    data class OnAlbumClick(val albumId: String): AlbumLazyLayoutAction
    data object OnStorageClick: AlbumLazyLayoutAction
}

@Composable
fun AlbumLazyLayout(
    modifier: Modifier = Modifier,
    state: AlbumLazyLayoutState,
    onAction: (AlbumLazyLayoutAction) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state.layoutType) {
            LayoutType.Grid -> {
                AlbumLazyGridLayout(
                    albums = state.albums,
                    onAction = onAction,
                    modifier = Modifier.fillMaxSize()
                )
            }
            LayoutType.List -> {
                AlbumLazyListLayout(
                    albums = state.albums,
                    onAction = onAction,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.error != null) {
            Text(
                text = "Đã xảy ra lỗi!",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun AlbumLazyGridLayout(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    onAction: (AlbumLazyLayoutAction) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MediaGridItem(
                modifier = Modifier,
                title = {
                    Text(
                        text = "Thư mục thiết bị",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                topSubtitle = {
                    Text(
                        text = "Storage",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                imageUrl = null,
                onClick = { onAction(AlbumLazyLayoutAction.OnStorageClick) }
            )
        }

        items(
            items = albums,
            key = { it.id }
        ) { album ->
            MediaGridItem(
                title = {
                    Text(
                        text = album.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                topSubtitle = {
                    Text(
                        text = album.artist,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                imageUrl = album.imageUrl,
                onClick = { onAction(AlbumLazyLayoutAction.OnAlbumClick(album.id)) }
            )
        }
    }
}

@Composable
fun AlbumLazyListLayout(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    onAction: (AlbumLazyLayoutAction) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            MediaListItem(
                modifier = Modifier
                    .padding(
                        horizontal = AppTheme.dimensions.paddingMedium,
                        vertical = AppTheme.dimensions.paddingSmall
                    ),
                title = {
                    Text(
                        text = "Thư mục thiết bị",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                subtitle = {
                    Text(
                        text = "Bộ nhớ trong",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                imageUrl = null,
                onClick = { onAction(AlbumLazyLayoutAction.OnStorageClick) }
            )
        }

        items(
            items = albums,
            key = { it.id },
        ) { album ->
            MediaListItem(
                modifier = Modifier
                    .padding(
                        horizontal = AppTheme.dimensions.paddingMedium,
                    ),
                title = {
                    Text(
                        text = album.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                subtitle = {
                    Text(
                        text = album.artist,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                imageUrl = album.imageUrl,
                onClick = { onAction(AlbumLazyLayoutAction.OnAlbumClick(album.id)) }
            )
        }
    }
}