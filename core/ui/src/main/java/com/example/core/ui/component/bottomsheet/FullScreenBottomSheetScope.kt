package com.example.core.ui.component.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

interface FullScreenBottomSheetScope {
    fun stickyContent(
        transaction: FullBottomSheetContentTransaction = FullScreenBottomSheetDefaults.Transaction.fade(
            collapsedAlpha = 1f,
            expandedAlpha = 0f
        ),
        content: @Composable () -> Unit
    )

    fun content(
        transaction: FullBottomSheetContentTransaction = FullScreenBottomSheetDefaults.Transaction.fade(
            collapsedAlpha = 0f,
            expandedAlpha = 1f
        ),
        content: @Composable (contentPadding: PaddingValues) -> Unit
    )
}

@Stable
internal class FullScreenBottomSheetScopeImpl : FullScreenBottomSheetScope {
    var stickySlot: @Composable (() -> Unit)? = null
    var stickyTransaction: FullBottomSheetContentTransaction = FullScreenBottomSheetDefaults.Transaction.fade(1f, 0f)

    var contentSlot: @Composable ((PaddingValues) -> Unit)? = null
    var contentTransaction: FullBottomSheetContentTransaction = FullScreenBottomSheetDefaults.Transaction.fade(0f, 1f)

    override fun stickyContent(
        transaction: FullBottomSheetContentTransaction,
        content: @Composable () -> Unit
    ) {
        this.stickySlot = content
        this.stickyTransaction = transaction
    }

    override fun content(
        transaction: FullBottomSheetContentTransaction,
        content: @Composable (contentPadding: PaddingValues) -> Unit
    ) {
        this.contentSlot = content
        this.contentTransaction = transaction
    }
}