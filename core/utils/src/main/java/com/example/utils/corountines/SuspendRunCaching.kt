package com.example.utils.corountines

import kotlinx.coroutines.CancellationException

inline fun <T> suspendRunCatching(block: () -> T): Result<T> {
    return runCatching {
        block()
    }.onFailure { exception ->
        if (exception is CancellationException) throw exception
    }
}