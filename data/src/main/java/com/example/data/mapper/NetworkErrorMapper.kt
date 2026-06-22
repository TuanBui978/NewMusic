package com.example.data.mapper

import com.example.domain.model.result.DataError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.serialization.SerializationException
import timber.log.Timber

fun Throwable.toNetworkError(): DataError.Network {
    Timber.d(this)
    return when (this) {
        is UnknownHostException, is java.net.ConnectException -> DataError.Network.NoInternet
        is SocketTimeoutException -> DataError.Network.RequestTimeout
        is IOException -> DataError.Network.NoInternet
        is HttpException -> when (code()) {
            401 -> DataError.Network.Unauthorized
            408 -> DataError.Network.RequestTimeout
            409 -> DataError.Network.Conflict
            413 -> DataError.Network.PayloadTooLarge
            429 -> DataError.Network.TooManyRequests
            in 500..599 -> DataError.Network.ServerError(code())
            else -> DataError.Network.Unknown
        }
        is SerializationException -> DataError.Network.Serialization
        else -> DataError.Network.Unknown
    }
}