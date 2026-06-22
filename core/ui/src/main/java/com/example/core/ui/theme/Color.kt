/**
 * Copyright (c) 2026.
 *
 * Author: TuanBuiQuang
 * Created: 3/7/26
 *
 * Module Owner: Android Core Team
 * Reviewer: Tech Lead
 */
package com.example.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.core.ui.theme.token.*


// Tạm thời giữ Light Theme để không lỗi hệ thống, dù Spotify không dùng
val lightColorScheme = lightColorScheme(
    primary = teal60,
    onPrimary = Color.White,
    background = neutral99,
    onBackground = neutral10,
    surface = neutral95,
    onSurface = neutral10,
    surfaceVariant = neutral90,
    onSurfaceVariant = neutral40,
    error = error40,
    onError = Color.White
)

// --- SPOTIFY PURE DARK THEME ---
val darkColorScheme = darkColorScheme(
    primary = teal60,              // Spotify Green: Dùng cho nút Play chính, Text đang Active
    onPrimary = neutral10,         // Chữ đen trên nền xanh (Ví dụ: Nút Shuffle play)
    primaryContainer = teal40,
    onPrimaryContainer = neutral99,

    secondary = neutral99,         // Trắng tinh: Dùng cho các icon thao tác (Next, Prev)
    onSecondary = neutral10,
    secondaryContainer = neutral40,
    onSecondaryContainer = neutral99,

    tertiary = sunny40,            // Vàng sáng cho Premium hoặc huy hiệu
    onTertiary = neutral10,

    background = neutral10,        // Nền Đen Sâu chủ đạo (#121212)
    onBackground = neutral99,      // Text chính màu trắng

    surface = neutral20,           // Nền xám đen cho Bottom Sheet, Card, Dialog (#181818)
    onSurface = neutral99,         // Text trên Card
    surfaceVariant = neutral40,    // Search bar, ô input (#282828)
    onSurfaceVariant = neutral80,  // Text phụ, tên ca sĩ, thời gian bài hát (#B3B3B3)
    outline = neutral60,           // Viền (Rất ít dùng trong Spotify)
    outlineVariant = neutral40,

    error = error40,
    onError = neutral99,
    errorContainer = error20,
    onErrorContainer = error80
)