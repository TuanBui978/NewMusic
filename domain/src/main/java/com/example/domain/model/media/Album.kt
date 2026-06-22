package com.example.domain.model.media

import java.util.UUID

class Album(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val artist: String,
    val imageUrl: String,
) {
}