package com.example.albumdetail.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme

data class AlbumDetailTopAppBarState(
    val title: String,
)

sealed interface AlbumDetailTopAppBarAction {
    object Back : AlbumDetailTopAppBarAction
}

data class AlbumDetailTopAppBarColors(
    val backgroundColor: Color,
    val gradientBlend: Brush
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailTopAppBar(
    modifier: Modifier = Modifier,
    state: AlbumDetailTopAppBarState,
    onAction: (AlbumDetailTopAppBarAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    collapseFraction: (() -> Float)? = null,
    colors: AlbumDetailTopAppBarColors = TopAppBarDefaults.albumDetailTopAppBarColors(),
) {
    val topAppBarState = scrollBehavior?.state ?: rememberTopAppBarState()
    val fractionProvider = collapseFraction ?: { topAppBarState.overlappedFraction }
    CenterAlignedTopAppBar(
        modifier = modifier
            .drawBehind {
                val fraction = fractionProvider()
                val bgAlpha = (fraction * 2f).coerceIn(0f, 1f)
                drawRect(
                    color = colors.backgroundColor,
                    alpha = bgAlpha
                )
                drawRect(
                    brush = colors.gradientBlend,
                    alpha = bgAlpha
                )
            },
        title = {
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = (-1f + fractionProvider() * 2f).coerceIn(0f, 1f)
                },
                text = state.title
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onAction(AlbumDetailTopAppBarAction.Back)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun AlbumDetailTopAppBarPreview() {
    AppTheme {
        AlbumDetailTopAppBar(
            state = AlbumDetailTopAppBarState(
                title = "Greatest Hits 2026",
            ),
            onAction = { action ->
                when (action) {
                    is AlbumDetailTopAppBarAction.Back -> {
                        println("Back clicked")
                    }
                }
            }
        )
    }
}