package com.example.albumdetail.ui.component.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.withoutVisualEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.util.fastFirstOrNull
import com.example.core.ui.util.LocalBottomOverlayInsets

private enum class AlbumLayoutSlot {
    TopBar, Floating, Content
}

private const val HeaderItemKey = "__album_layout_header__"
private const val ActionsItemKey = "__album_layout_actions__"

@Composable
fun AlbumLayout(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = WindowInsets.navigationBars
        .union(LocalBottomOverlayInsets.current)
        .asPaddingValues(),
    topBar: @Composable (collapseFraction: () -> Float) -> Unit = {},
    header: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    floatingActionButton: @Composable () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    val floatingContent: @Composable () -> Unit = remember(floatingActionButton) {
        { Box { floatingActionButton() } }
    }
    val overscrollEffect = rememberOverscrollEffect()
    val listOverscrollEffect = remember(overscrollEffect) {
        overscrollEffect?.withoutVisualEffect()
    }

    SubcomposeLayout(modifier = modifier.overscroll(overscrollEffect)) { constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        var topBarHeightPx = 0
        val collapseFractionProvider: () -> Float = {
            val headerItem = listState.layoutInfo.visibleItemsInfo
                .fastFirstOrNull { it.key == HeaderItemKey }
            when {
                headerItem != null -> {
                    val collapseRange = (headerItem.size - topBarHeightPx).coerceAtLeast(1)
                    ((-headerItem.offset).toFloat() / collapseRange).coerceIn(0f, 1f)
                }
                listState.firstVisibleItemIndex > 0 -> 1f
                else -> 0f
            }
        }

        // 1. Đo TopBar để lấy mốc ghim nút Play và chừa khoảng đệm đầu cho Header
        val topBarPlaceables = subcompose(AlbumLayoutSlot.TopBar) {
            topBar(collapseFractionProvider)
        }.map { it.measure(looseConstraints) }
        topBarHeightPx = topBarPlaceables.maxOfOrNull { it.height } ?: 0
        val topBarHeightDp = topBarHeightPx.toDp()

        // 2. Đo FAB trước để biết chính xác diện tích cần nhường bên phải hàng Actions
        val fabPlaceable = subcompose(AlbumLayoutSlot.Floating, floatingContent)
            .first()
            .measure(looseConstraints)
        val fabWidthDp = fabPlaceable.width.toDp()
        val fabHeightDp = fabPlaceable.height.toDp()

        // 3. Một LazyColumn duy nhất ôm trọn Header + Actions + Content để vuốt mượt xuyên suốt
        val contentPlaceable = subcompose(AlbumLayoutSlot.Content) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                overscrollEffect = listOverscrollEffect,
            ) {
                item(key = HeaderItemKey) {
                    Box(modifier = Modifier.padding(top = topBarHeightDp)) {
                        header()
                    }
                }
                item(key = ActionsItemKey) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = fabHeightDp)
                            .padding(end = fabWidthDp)
                            .graphicsLayer {
                                val actionsItem = listState.layoutInfo.visibleItemsInfo
                                    .fastFirstOrNull { it.key == ActionsItemKey }
                                if (actionsItem != null && topBarHeightPx > 0) {
                                    val distanceToTopBar = (actionsItem.offset - topBarHeightPx).toFloat()
                                    val fadeRange = (actionsItem.size / 2f).coerceAtLeast(1f)
                                    alpha = (1f + distanceToTopBar / fadeRange).coerceIn(0f, 1f)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        content = actions
                    )
                }
                content()
            }
        }.first().measure(constraints)

        val fabX = (layoutWidth - fabPlaceable.width).coerceAtLeast(0)
        val pinnedY = if (topBarHeightPx > 0) {
            topBarHeightPx - (fabPlaceable.height / 2)
        } else {
            0
        }

        layout(layoutWidth, layoutHeight) {
            contentPlaceable.placeRelative(0, 0)
            topBarPlaceables.forEach { it.placeRelative(0, 0) }
            fabPlaceable.placeRelativeWithLayer(fabX, 0) {
                val actionsItem = listState.layoutInfo.visibleItemsInfo
                    .fastFirstOrNull { it.key == ActionsItemKey }

                val currentY = when {
                    actionsItem != null -> {
                        actionsItem.offset + Alignment.CenterVertically.align(
                            size = fabPlaceable.height,
                            space = actionsItem.size
                        )
                    }
                    listState.firstVisibleItemIndex > 1 -> pinnedY
                    else -> layoutHeight
                }

                translationY = maxOf(currentY, pinnedY).toFloat()
            }
        }
    }
}

