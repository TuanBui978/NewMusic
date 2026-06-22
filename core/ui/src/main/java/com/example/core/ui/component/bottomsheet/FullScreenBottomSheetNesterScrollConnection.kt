package com.example.core.ui.component.bottomsheet

import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.animateToWithDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
internal class FullScreenBottomSheetNestedScrollConnection(
    private val state: FullScreenBottomSheetState,
    private val coroutineScope: CoroutineScope
) : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y
        if (source == NestedScrollSource.UserInput) {
            if (state.expandedFraction > 0f && state.expandedFraction < 1f) {
                return Offset(0f, state.dispatchRawDelta(delta))
            }
        }
        return Offset.Zero
    }
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val delta = available.y
        if (source == NestedScrollSource.UserInput) {
            if (delta > 0 && state.expandedFraction == 1f) {
                return Offset(0f, state.dispatchRawDelta(delta) )
            }
        }
        return Offset.Zero
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        if (state.expandedFraction > 0f && state.expandedFraction < 1f) {
            val velocityY = available.y
            val currentOffset = state.delegateState.offset.takeIf { !it.isNaN() } ?: 0f
            val decaySpec = AnchoredDraggableDefaults.DecayAnimationSpec
            val projectedOffset = decaySpec.calculateTargetValue(
                initialValue = currentOffset,
                initialVelocity = velocityY
            )
            val targetState = state.delegateState.anchors.closestAnchor(projectedOffset)
                ?: if (velocityY > 0f) SheetValue.Collapsed else SheetValue.Expanded
            coroutineScope.launch {
                state.delegateState.animateToWithDecay(
                    targetValue = targetState,
                    velocity = velocityY
                )
            }
            return available
        }
        return Velocity.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        val velocityY = available.y
        if (velocityY > 0f && state.expandedFraction == 1f) {
            val currentOffset = state.delegateState.offset.takeIf { !it.isNaN() } ?: 0f
            val decaySpec = AnchoredDraggableDefaults.DecayAnimationSpec
            val projectedOffset = decaySpec.calculateTargetValue(
                initialValue = currentOffset,
                initialVelocity = velocityY
            )
            val targetState = state.delegateState.anchors.closestAnchor(projectedOffset)
                ?: SheetValue.Collapsed
            coroutineScope.launch {
                state.delegateState.animateToWithDecay(
                    targetValue = targetState,
                    velocity = velocityY
                )
            }
            return available
        }

        return Velocity.Zero
    }
}

@Composable
internal fun rememberFullScreenBottomSheetScrollConnection(state: FullScreenBottomSheetState): FullScreenBottomSheetNestedScrollConnection {
    val scope = rememberCoroutineScope()
    return remember(state, scope) { FullScreenBottomSheetNestedScrollConnection(state, scope) }
}