package com.example.core.navigation.router.album

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@Serializable
data class AlbumDetailRoute @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String
)

@Serializable
data object LocalAlbumDetailRoute