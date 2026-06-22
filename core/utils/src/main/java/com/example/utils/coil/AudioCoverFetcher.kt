package com.example.utils.coil

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Size
import coil3.ImageLoader
import coil3.decode.DataSource
import coil3.fetch.FetchResult
import coil3.request.Options
import coil3.fetch.Fetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import coil3.asImage
import coil3.fetch.ImageFetchResult

class AudioCoverFetcher(
    private val uri: Uri,
    private val context: Context
) : Fetcher {
    override suspend fun fetch(): FetchResult? {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    context.contentResolver.loadThumbnail(uri, Size(512, 512), null)
                } else {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(context, uri)
                    val bytes = retriever.embeddedPicture
                    retriever.release()
                    bytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                }

                if (bitmap != null) {
                    ImageFetchResult(
                        image = bitmap.asImage(),
                        isSampled = false,
                        dataSource = DataSource.DISK
                    )
                } else null
            } catch (_: Exception) {
                null
            }
        }
    }

    class Factory(private val context: Context) : Fetcher.Factory<Uri> {
        override fun create(data: Uri, options: Options, imageLoader: ImageLoader): Fetcher? {
            if (data.scheme == "content" && data.toString().contains("audio")) {
                return AudioCoverFetcher(data, context)
            }
            return null
        }
    }
}