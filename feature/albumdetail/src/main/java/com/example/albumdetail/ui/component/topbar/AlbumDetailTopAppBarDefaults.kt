package com.example.albumdetail.ui.component.topbar

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.core.ui.theme.AppTheme

@Composable
fun TopAppBarDefaults.albumDetailTopAppBarColors(color: Color = Color(0xFF20284D)) = AlbumDetailTopAppBarColors(
    backgroundColor = color,
    gradientBlend = AppTheme.extraGradient.backgroundFadeGradient
)