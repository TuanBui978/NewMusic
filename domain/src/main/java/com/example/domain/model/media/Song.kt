package com.example.domain.model.media

import java.util.UUID

data class Song(
    val id: String = UUID.randomUUID().toString(),
    val albumId: UUID? = null,
    val name: String = "",
    val artist: String = "",
    val contentUri: String = "",
    val path: String = "",
    val duration: Long = 0L,
) {
}