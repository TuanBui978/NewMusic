package com.example.domain.model.result

sealed interface Result<out D, out E> {
    data class Success<D>(val data: D) : Result<D, Nothing>
    data class Error<E>(val error: E) : Result<Nothing, E>
    data object Loading : Result<Nothing, Nothing>
}