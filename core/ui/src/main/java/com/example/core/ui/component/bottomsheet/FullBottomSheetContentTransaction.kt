package com.example.core.ui.component.bottomsheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
fun interface FullBottomSheetContentTransaction {
    fun GraphicsLayerScope.startTransaction(state: FullScreenBottomSheetState)
    operator fun plus(other: FullBottomSheetContentTransaction) = FullBottomSheetContentTransaction { state ->
        this.run { startTransaction(state) }
        other.run { startTransaction(state) }
    }
}

