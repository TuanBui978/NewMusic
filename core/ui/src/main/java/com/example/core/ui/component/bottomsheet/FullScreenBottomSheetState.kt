package com.example.core.ui.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class SheetValue { Collapsed, Expanded }

@OptIn(ExperimentalFoundationApi::class)
@Stable
class FullScreenBottomSheetState internal constructor(initialValue: SheetValue) {

    internal val delegateState = AnchoredDraggableState(
        initialValue = initialValue
    )

    var stickyHeightPx by mutableIntStateOf(0)
        internal set

    val expandedFraction: Float
        get() {
            val maxOffset = delegateState.anchors.maxPosition()
            val minOffset = delegateState.anchors.minPosition()
            if (maxOffset.isNaN() || minOffset.isNaN() || maxOffset <= minOffset) return 0f
            val currentOffset = delegateState.offset.coerceIn(minOffset, maxOffset)
            return (maxOffset - currentOffset) / (maxOffset - minOffset)
        }

    val collapsedFraction: Float
        get() = 1f - expandedFraction

    val isExpanded: Boolean
        get() = delegateState.targetValue == SheetValue.Expanded ||
                delegateState.currentValue == SheetValue.Expanded

    suspend fun expand() {
        delegateState.animateTo(SheetValue.Expanded)
    }

    suspend fun collapse() {
        delegateState.animateTo(SheetValue.Collapsed)
    }

    fun dispatchRawDelta(delta: Float): Float {
        return delegateState.dispatchRawDelta(delta)
    }
}

@Composable
fun rememberFullScreenBottomSheetState(
    initialValue: SheetValue = SheetValue.Collapsed
): FullScreenBottomSheetState {
    return remember { FullScreenBottomSheetState(initialValue) }
}