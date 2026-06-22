package com.example.core.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<UiState, UiAction, UiEvent>(
    initialState: UiState
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>(capacity = Channel.BUFFERED, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val uiEvent = _uiEvent.receiveAsFlow()

    abstract fun handleAction(action: UiAction)

    protected fun updateState(reducer: UiState.() -> UiState) {
        _uiState.update {
            it.reducer()
        }
    }

    protected fun sendEvent(event: UiEvent) {
        _uiEvent.trySend(event)
    }
}