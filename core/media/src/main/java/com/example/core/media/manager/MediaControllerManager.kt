package com.example.core.media.manager

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.core.media.service.PlaybackService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaControllerManager(
    private val context: Context
) {
    private val _player = MutableStateFlow<Player?>(null)
    val player = _player.asStateFlow()

    init {
        initialController()
    }

    private fun initialController() {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, PlaybackService::class.java)
        )
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                _player.value = controllerFuture.get()
            },
            ContextCompat.getMainExecutor(context)
        )
    }
}