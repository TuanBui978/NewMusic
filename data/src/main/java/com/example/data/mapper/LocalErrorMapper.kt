package com.example.data.mapper

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import com.example.domain.model.result.DataError
import timber.log.Timber
import java.io.IOException

fun Throwable.toLocalError(): DataError.Local {
    Timber.d(this)
    return when (this) {
        is SQLiteDiskIOException -> DataError.Local.DiskFull
        is SQLiteException -> DataError.Local.CorruptedData
        is SecurityException -> DataError.Local.PermissionDenied
        is java.io.FileNotFoundException -> DataError.Local.ItemNotFound
        is IOException -> DataError.Local.Unknown
        else -> DataError.Local.Unknown
    }
}