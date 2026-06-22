package com.example.core.ui.component.topbar.standard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarTopAppBar(
    title: @Composable () -> Unit,
    avatarUrl: String? = null,
    onAvatarClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = title,
        actions = actions,
        navigationIcon = {
            val painter = rememberAsyncImagePainter(model = avatarUrl)
            val iconPainter = rememberVectorPainter(image = Icons.Rounded.AccountCircle)
            val iconTint = AppTheme.colorScheme.onSurfaceVariant
            Image(
                modifier = Modifier
                    .clickable(
                        onClick = onAvatarClick,
                    )
                    .clip(shape = CircleShape)
                    .size(AppTheme.dimensions.imageSizeMedium)
                    .drawWithContent {
                        if (painter.state.value !is AsyncImagePainter.State.Success) {
                            with(iconPainter) {
                                draw(
                                    size = size,
                                    colorFilter = ColorFilter.tint(iconTint)
                                )
                            }
                        }
                        drawContent()
                    },
                painter = painter,
                contentDescription = null,
            )
        },
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

