package com.example.albumdetail.ui.local

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.albumdetail.ui.component.media.SongListItem
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBar
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBarAction
import com.example.albumdetail.ui.component.topbar.AlbumDetailTopAppBarState
import com.example.albumdetail.ui.component.topbar.AlbumHeader
import com.example.albumdetail.ui.component.topbar.AlbumLayout
import com.example.core.ui.component.layout.BoxState
import com.example.core.ui.component.layout.StateBox
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.event.ObserveEvents
import com.example.core.utils.permission.AppPermissions
import com.example.core.utils.permission.rememberPermissionAction
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
        onAction = viewModel::handleAction
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    AlbumLayout(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        listState = listState,
        topBar = {
            AlbumDetailTopAppBar(
                state = AlbumDetailTopAppBarState(
                    title = state.title,
                ),
                onAction = { action ->
                    if (action is AlbumDetailTopAppBarAction.Back) {
                        onAction(LocalAlbumDetailUiAction.OnBack)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        playButton = {
            IconButton(
                onClick = { /* Phát nhạc */ },
                modifier = Modifier
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
        },
        header = {
            AlbumHeader(
                title = state.title,
                subtitle = state.subtitle,
            )
        }
    ) { headerHeight ->
        BoxState(state.isLoading, errorMessage = null)?.let {
            StateBox(
                state = it,
                onRetry = { onAction(LocalAlbumDetailUiAction.OnPermissionAllow) }
            )
        } ?: LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            item {
                Spacer(modifier = Modifier.height(headerHeight))
            }
            items(state.songs) {
                SongListItem(
                    title = it.name,
                    artist = it.artist,
                    onItemClick = { onAction(LocalAlbumDetailUiAction.OnSongClick(it)) }
                )
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

