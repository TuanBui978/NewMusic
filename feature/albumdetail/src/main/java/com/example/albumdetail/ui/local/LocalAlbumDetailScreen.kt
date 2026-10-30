package com.example.albumdetail.ui.local

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.albumdetail.ui.component.layout.AlbumLayout
import com.example.albumdetail.ui.component.media.SongListItem
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBar
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBarAction
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBarState
import com.example.albumdetail.ui.component.topbar.AlbumHeader
import com.example.core.ui.component.layout.BoxState
import com.example.core.ui.component.layout.StateBox
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.event.ObserveEvents
import com.example.core.utils.permission.AppPermissions
import com.example.core.utils.permission.rememberPermissionAction
import com.example.domain.model.media.Song
import kotlinx.coroutines.flow.Flow

@Composable
fun LocalAlbumDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LocalAlbumDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ScreenLayout(
        modifier = modifier,
        state = state,
        onAction = { action ->
            if (action is LocalAlbumDetailUiAction.OnBack) {
                navController.popBackStack()
            } else {
                viewModel.handleAction(action)
            }
        }
    )
    EventHandler(
        flow = viewModel.uiEvent,
        onAction = viewModel::handleAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenLayout(
    modifier: Modifier = Modifier,
    state: LocalAlbumDetailUiState,
    onAction: (LocalAlbumDetailUiAction) -> Unit,
) {
    val listState = rememberLazyListState()

    AlbumLayout(
        modifier = modifier.fillMaxSize(),
        listState = listState,
        topBar = { collapseFraction ->
            AlbumDetailTopAppBar(
                state = AlbumDetailTopAppBarState(
                    title = state.title.ifEmpty { "Local Music" },
                ),
                onAction = { action ->
                    if (action is AlbumDetailTopAppBarAction.Back) {
                        onAction(LocalAlbumDetailUiAction.OnBack)
                    }
                },
                collapseFraction = collapseFraction
            )
        },
        header = {
            AlbumHeader(
                title = state.title.ifEmpty { "Local Music" },
                subtitle = state.subtitle ?: "${state.songs.size} bài hát",
            )
        },
        actions = {
            Spacer(modifier = Modifier.width(AppTheme.dimensions.paddingMedium))
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = Color.LightGray,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = Color.LightGray,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimensions.spaceExtraLarge))
        },
        floatingActionButton = {
            IconButton(
                onClick = { /* Phát nhạc */ },
                modifier = Modifier
                    .padding(end = AppTheme.dimensions.paddingMedium)
                    .size(AppTheme.dimensions.iconExtraLarge)
                    .background(Color(0xFF1ED760), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.Black,
                    modifier = Modifier.size(AppTheme.dimensions.iconLarge)
                )
            }
        }
    ) {
        BoxState(state.isLoading, errorMessage = null)?.let { boxState ->
            item(key = "state_box") {
                StateBox(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = AppTheme.dimensions.spaceExtraLarge),
                    state = boxState,
                    onRetry = { onAction(LocalAlbumDetailUiAction.OnPermissionAllow) }
                )
            }
        } ?: run {
            if (state.songs.isNotEmpty()) {
                items(state.songs, key = { it.id }) { song ->
                    SongListItem(
                        title = song.name,
                        artist = song.artist,
                        onItemClick = { onAction(LocalAlbumDetailUiAction.OnSongClick(song)) }
                    )
                }
            } else {
                items(50) { index ->
                    SongListItem(
                        title = "Bài hát demo #$index",
                        artist = "Nghệ sĩ demo",
                        onItemClick = { onAction(LocalAlbumDetailUiAction.OnSongClick(Song())) }
                    )
                }
            }
        }
    }
}

@Composable
fun EventHandler(
    flow: Flow<LocalAlbumDetailUiEvent>,
    onAction: (LocalAlbumDetailUiAction) -> Unit
) {
    val permissionAction = rememberPermissionAction(
        AppPermissions.getMusicPermissions()
    ) {
        onAction(LocalAlbumDetailUiAction.OnPermissionAllow)
    }
    ObserveEvents(
        flow = flow
    ) {
        when (it) {
            LocalAlbumDetailUiEvent.RequestPermission -> {
                permissionAction()
            }
        }
    }
}


