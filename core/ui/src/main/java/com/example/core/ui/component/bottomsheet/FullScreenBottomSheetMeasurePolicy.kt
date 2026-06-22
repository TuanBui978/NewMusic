package com.example.core.ui.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.SubcomposeMeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxOfOrDefault

internal enum class SheetSlots { Sticky, Content }

@OptIn(ExperimentalFoundationApi::class)
internal fun SubcomposeMeasureScope.measureFullScreenBottomSheet(
    constraints: Constraints,
    state: FullScreenBottomSheetState,
    scope: FullScreenBottomSheetScopeImpl
): MeasureResult {
    val contentConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val stickyMeasurables = subcompose(SheetSlots.Sticky) {
        scope.stickySlot?.invoke()
    }
    val stickyPlaceable = stickyMeasurables.fastMap { it.measure(contentConstraints) }
    val stickyHeightPx = stickyPlaceable.fastMaxOfOrDefault(0) { it.height }
    val stickyHeightDp = stickyHeightPx.toDp()

    val contentMeasurables = subcompose(SheetSlots.Content) {
        scope.contentSlot?.invoke(PaddingValues(top = stickyHeightDp))
    }
    val contentPlaceable = contentMeasurables.fastMap { it.measure(contentConstraints) }
    val contentHeight = contentPlaceable.fastMaxOfOrDefault(0) { it.height }
    val sheetMaxHeight = maxOf(contentHeight, stickyHeightPx)
    val expandedOffset = (constraints.maxHeight - sheetMaxHeight).coerceAtLeast(0).toFloat()
    val collapsedOffset = (constraints.maxHeight - stickyHeightPx).toFloat()
    val newAnchors = DraggableAnchors {
        SheetValue.Collapsed at collapsedOffset
        SheetValue.Expanded at expandedOffset
    }
    state.delegateState.updateAnchors(newAnchors)
    return layout(constraints.maxWidth, sheetMaxHeight) {
        contentPlaceable.fastForEach { placeable ->
            placeable.placeRelativeWithLayer(0, 0) {
                with(scope.contentTransaction) {
                    startTransaction(state)
                }
            }
        }

        stickyPlaceable.fastForEach { placeable ->
            placeable.placeRelativeWithLayer(0, 0) {
                with(scope.stickyTransaction) {
                    startTransaction(state)
                }
            }
        }
    }
}