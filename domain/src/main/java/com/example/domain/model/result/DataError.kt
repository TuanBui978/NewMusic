package com.example.domain.model.result

sealed interface DataError {
    sealed interface Network : DataError {
        data object RequestTimeout : Network
        data object Unauthorized : Network
        data object Conflict : Network
        data object TooManyRequests : Network
        data object NoInternet : Network
        data object PayloadTooLarge : Network
        data class ServerError(val code: Int) : Network
        data object Serialization : Network
        data object Unknown : Network
    }

    sealed interface Local : DataError {
        data object DiskFull : Local
        data object ItemNotFound : Local
        data object CorruptedData : Local
        data object PermissionDenied : Local
        data object Unknown : Local
    }

    sealed interface Authentication : DataError {
        data object WrongPassword : Authentication
        data object UserNotFound : Authentication
        data object AccountLocked : Authentication
        data object InvalidSession : Authentication
        data class NetworkError(val baseError: Network) : Authentication
    }

    sealed interface Resource : DataError {
        data class NetworkError(val error: Network) : Resource
        data class LocalError(val error: Local) : Resource
    }

    /*
    sealed interface MediaPlayback : DataError {
        data object CodecNotSupported : MediaPlayback
        data object SourceUnreachable : MediaPlayback
    }
    */
}