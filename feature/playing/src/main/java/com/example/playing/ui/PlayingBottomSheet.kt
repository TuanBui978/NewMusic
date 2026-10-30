package com.example.playing.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import com.example.core.media.manager.PlayBackState
import com.example.core.media.manager.rememberPlayBackState
import com.example.core.ui.component.bottomsheet.FullScreenBottomSheet
import com.example.core.ui.component.bottomsheet.FullScreenBottomSheetState
import com.example.core.ui.component.bottomsheet.rememberFullScreenBottomSheetState
import com.example.core.ui.component.mediaplayer.MiniMediaPlayer
import com.example.core.ui.theme.AppTheme

@Composable
fun PlayingBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: FullScreenBottomSheetState = rememberFullScreenBottomSheetState(),
    viewModel: PlayingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val playBackState = rememberPlayBackState(viewModel.playerStateManager)
    ScreenLayout(
        modifier = modifier,
        sheetState = sheetState,
        state = state,
        playBackState = playBackState,
        onAction = {
            when (it) {
                is PlayingUiAction.OnFavoriteClick -> {
                    viewModel.handleAction(it)
                }
            }
        }
    )
}

@Composable
fun ScreenLayout(
    modifier: Modifier = Modifier,
    sheetState: FullScreenBottomSheetState = rememberFullScreenBottomSheetState(),
    state: PlayingUiState,
    playBackState: PlayBackState,
    onAction: (PlayingUiAction) -> Unit = {}
) {
    FullScreenBottomSheet(
        modifier = modifier,
        state = sheetState
    ) {
        stickyContent {
            MiniMediaPlayer(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimensions.paddingSmall),
                player = playBackState.player,
                image = playBackState.currentMedia?.albumArtUri,
                title = playBackState.currentMedia?.title ?: "",
                subtitle = playBackState.currentMedia?.artist,
                progress = { playBackState.currentPosition().toFloat() / (playBackState.currentMedia?.duration ?: -1) },
                isFavorite = state.isFavorite,
                onFavoriteClick = {
                    onAction(PlayingUiAction.OnFavoriteClick(!it))
                }
            )
        }
        content {
            Box(
                modifier = Modifier
                    .height(height = 400.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Content")
            }
        }
    }
}