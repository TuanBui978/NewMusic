package com.example.core.utils.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * A lifecycle-aware Composable utility to observe and handle one-off events from a [Flow].
 *
 * This is ideal for handling UI "Side Effects" such as navigation, showing SnackBars,
 * or displaying dialogs that should only be processed when the UI is in a [Lifecycle.State.STARTED] state.
 *
 * @param T The type of event being observed.
 * @param flow The [Flow] stream of events (usually a SharedFlow or Channel-backed Flow).
 * @param key Optional keys to restart the [LaunchedEffect] if they change.
 * @param onEvent A suspend lambda triggered whenever a new event is emitted.
 * Executed on [Dispatchers.Main] to ensure UI-thread safety.
 */
@Composable
fun <T> ObserveEvents(
    flow: Flow<T>,
    vararg key: Any,
    onEvent: suspend CoroutineScope.(event: T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // rememberUpdatedState ensures that if onEvent changes, the LaunchedEffect
    // doesn't restart, but always uses the latest provided lambda.
    val onCurrentEvents by rememberUpdatedState(onEvent)

    LaunchedEffect(flow, lifecycleOwner.lifecycle, *key) {
        // Safely collect from the flow only when the lifecycle is at least STARTED.
        // This prevents processing events when the app is in the background.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { event ->
                onCurrentEvents(event)
            }
        }
    }
}