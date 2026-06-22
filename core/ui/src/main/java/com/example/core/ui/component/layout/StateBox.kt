package com.example.core.ui.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.core.ui.theme.AppTheme

sealed class BoxState {
    internal data object Loading : BoxState()
    internal data class Error(val message: String) : BoxState()
}

fun BoxState(isLoading: Boolean, errorMessage: String? = null): BoxState? {
    return when {
        isLoading -> BoxState.Loading
        errorMessage != null -> BoxState.Error(errorMessage)
        else -> null
    }
}

object StateBoxDefaults {
    @Composable
    fun LoadingContent(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun ErrorContent(
        modifier: Modifier = Modifier,
        message: String,
        onRetry: (() -> Unit)? = null,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(AppTheme.dimensions.paddingMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Lỗi",
                tint = AppTheme.colorScheme.error,
                modifier = Modifier.size(AppTheme.dimensions.iconMedium)
            )
            Text(
                text = message,
                style = AppTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingMedium)
            )
            if (onRetry != null) {
                Button(onClick = onRetry) {
                    Text("Thử lại")
                }
            }
        }
    }
}

@Composable
fun StateBox(
    modifier: Modifier = Modifier,
    state: BoxState,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { StateBoxDefaults.LoadingContent() },
    errorContent: @Composable (String) -> Unit = { errorMessage ->
        StateBoxDefaults.ErrorContent(message = errorMessage, onRetry = onRetry)
    },
) {
    Box(modifier = modifier) {
        when (state) {
            BoxState.Loading -> loadingContent()
            is BoxState.Error -> errorContent(state.message)
        }
    }
}