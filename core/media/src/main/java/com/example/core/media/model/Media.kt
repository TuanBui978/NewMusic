package com.example.core.media.model

data class Media(
    val title: String,
    val artist: String,
    val albumArtUri: Any?,
    val trackUri: String,
    val duration: Long
)