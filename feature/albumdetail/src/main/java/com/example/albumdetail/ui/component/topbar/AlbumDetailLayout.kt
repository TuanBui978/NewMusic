package com.example.albumdetail.ui.component.topbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxOfOrDefault

private enum class SpotifySlot { TopBar, Header, PlayButton, List }

@Composable
fun AlbumLayout(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    topBar: @Composable () -> Unit,
    playButton: @Composable () -> Unit,
    header: @Composable () -> Unit,
    contentList: @Composable (headerHeight: Dp) -> Unit
) {
    val density = LocalDensity.current
    SubcomposeLayout(modifier = modifier) { constraints ->
        val topBarPlaceables = subcompose(SpotifySlot.TopBar, topBar)
            .fastMap { it.measure(constraints) }
        val topBarHeightPx = topBarPlaceables.fastMaxOfOrDefault(0) { it.height }
        val playBtnPlaceables = subcompose(SpotifySlot.PlayButton, playButton)
            .fastMap { it.measure(constraints) }
        val playBtnHeightPx = playBtnPlaceables.fastMaxOfOrDefault(0) { it.height }
        val playBtnWidthPx = playBtnPlaceables.fastMaxOfOrDefault(0) { it.width }
        val headerPlaceables = subcompose(SpotifySlot.Header, header)
            .fastMap { it.measure(constraints) }
        val headerHeightPx = headerPlaceables.fastMaxOfOrDefault(0) { it.height }
        val headerHeightDp = with(density) { headerHeightPx.toDp() }
        val listPlaceables = subcompose(SpotifySlot.List) {
            contentList(headerHeightDp)
        }.fastMap { it.measure(constraints) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            listPlaceables.fastForEach { placeable ->
                placeable.placeRelative(0, 0)
            }
            headerPlaceables.fastForEach { placeable ->
                placeable.placeWithLayer(x = 0, y = 0) {
                    val scrollOffset = if (listState.firstVisibleItemIndex == 0) {
                        listState.firstVisibleItemScrollOffset.toFloat()
                    } else headerHeightPx.toFloat()

                    translationY = -scrollOffset
                }
            }
            val startX = constraints.maxWidth - playBtnWidthPx
            val startY = headerHeightPx - playBtnHeightPx
            val pinnedY = topBarHeightPx - (playBtnHeightPx / 2f)
            playBtnPlaceables.fastForEach { placeable ->
                placeable.placeWithLayer(x = startX, y = 0) {
                    val scrollOffset = if (listState.firstVisibleItemIndex == 0) {
                        listState.firstVisibleItemScrollOffset.toFloat()
                    } else {
                        constraints.maxHeight.toFloat()
                    }
                    val currentY = startY - scrollOffset
                    translationY = maxOf(currentY, pinnedY)
                }
            }
            topBarPlaceables.fastForEach { placeable ->
                placeable.placeRelative(0, 0)
            }
        }
    }
}