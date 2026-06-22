package com.example.core.ui.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

object FullScreenBottomSheetDefaults {

    object Transaction {

        fun fade(
            collapsedAlpha: Float,
            expandedAlpha: Float
        ) = FullBottomSheetContentTransaction { state ->
            alpha = collapsedAlpha + (expandedAlpha - collapsedAlpha) * state.expandedFraction
        }

        fun scaleAndFade(
            collapsedScale: Float = 0.9f,
            expandedScale: Float = 1.0f,
            collapsedAlpha: Float = 0f,
            expandedAlpha: Float = 1f
        ) = FullBottomSheetContentTransaction { state ->
            alpha = collapsedAlpha + (expandedAlpha - collapsedAlpha) * state.expandedFraction

            val currentScale = collapsedScale + (expandedScale - collapsedScale) * state.expandedFraction
            scaleX = currentScale
            scaleY = currentScale
        }

        fun parallaxSticky(
            parallaxDistance: Float = 150f,
            collapsedAlpha: Float = 1f,
            expandedAlpha: Float = 0.5f
        ) = FullBottomSheetContentTransaction { state ->
            alpha = collapsedAlpha + (expandedAlpha - collapsedAlpha) * state.expandedFraction
            translationY = -(parallaxDistance * state.expandedFraction)
        }

        fun dynamicShape(
            collapsedCornerRadius: Dp = 24.dp,
            expandedCornerRadius: Dp = 0.dp
        ) = FullBottomSheetContentTransaction { state ->
            val currentRadius = collapsedCornerRadius + (expandedCornerRadius - collapsedCornerRadius) * state.expandedFraction
            shape = RoundedCornerShape(
                topStart = currentRadius,
                topEnd = currentRadius
            )
            clip = true
        }
    }
}

@Composable
fun FullScreenBottomSheet(
    modifier: Modifier = Modifier,
    state: FullScreenBottomSheetState,
    content: FullScreenBottomSheetScope.() -> Unit
) {
    val scope = remember(content) {
        FullScreenBottomSheetScopeImpl().apply {
            content()
        }
    }
    val connection = rememberFullScreenBottomSheetScrollConnection(state)
    SubcomposeLayout(
        modifier = modifier
            .offset {
                val yOffset = state.delegateState.offset.takeIf { !it.isNaN() } ?: 0f
                IntOffset(0, yOffset.roundToInt())
            }
            .nestedScroll(connection)
            .anchoredDraggable(state.delegateState, Orientation.Vertical)
    ) {
        measureFullScreenBottomSheet(it, state, scope)
    }
}