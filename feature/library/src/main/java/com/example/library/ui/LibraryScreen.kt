package com.example.library.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.navigation.router.album.LocalAlbumDetailRoute
import com.example.core.ui.component.topbar.standard.AvatarTopAppBar
import com.example.core.ui.component.topbar.standard.defaultAppTitle
import com.example.core.utils.event.ObserveEvents
import com.example.library.ui.component.AlbumLazyLayout
import com.example.library.ui.component.AlbumLazyLayoutAction
import com.example.library.ui.component.AlbumLazyLayoutState
import com.example.library.ui.component.LayoutType
import com.example.resource.ResStrings
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Serializable
data object LibraryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ScreenLayout(
        modifier = modifier,
        uiState = uiState,
        onAction = {
            when (it) {
                LibraryUiAction.OnLayoutTypeChange -> viewModel.handleAction(LibraryUiAction.OnLayoutTypeChange)
                LibraryUiAction.OnStorageClick -> navController.navigate(LocalAlbumDetailRoute) {
                    launchSingleTop = true
                }
            }
        }
    )
    ObserveEvents(
        flow = viewModel.uiEvent,
        onEvent = {
            eventHandle(it, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLayout(
    modifier: Modifier = Modifier,
    uiState: LibraryUiState,
    onAction: (LibraryUiAction) -> Unit
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Column {
                AvatarTopAppBar(
                    title = TopAppBarDefaults.defaultAppTitle(stringResource(ResStrings.library)),
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onAction(LibraryUiAction.OnLayoutTypeChange)
                    }
                ) {
                    AnimatedContent(
                        targetState = uiState.layoutType,
                        label = "LayoutToggleAnimation"
                    ) { currentLayout ->
                        if (currentLayout == LayoutType.List) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = "Switch to Grid View"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ViewList,
                                contentDescription = "Switch to List View"
                            )
                        }
                    }
                }
            }
            AlbumLazyLayout(
                modifier = modifier.weight(1f),
                state = AlbumLazyLayoutState(
                    albums = uiState.albums,
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    layoutType = uiState.layoutType
                ),
                onAction = {
                    when (it) {
                        is AlbumLazyLayoutAction.OnAlbumClick -> {}
                        is AlbumLazyLayoutAction.OnStorageClick -> onAction(LibraryUiAction.OnStorageClick)
                    }
                }
            )
        }
    }
}

private fun CoroutineScope.eventHandle(
    event: LibraryUiEvent,
    navController: NavController
) {
    when (event) {
        is LibraryUiEvent.NavigateToStorage -> {
            navController.navigate(LocalAlbumDetailRoute) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

