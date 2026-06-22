package com.example.core.ui.component.topbar.standard

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.core.ui.theme.AppTheme

@Composable
fun TopAppBarDefaults.defaultAppTitle(
    title: String,
    color: Color = Color.Unspecified,
    style: TextStyle = AppTheme.typography.titleLarge
) : @Composable () -> Unit = {
    Text(
        text = title,
        style = style,
        color = color,
    )
}