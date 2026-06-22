/**
 * Copyright (c) 2026.
 *
 * Author: TuanBuiQuang
 * Created: 3/7/26
 *
 * Module Owner: Android Core Team
 * Reviewer: Tech Lead
 */
package com.example.core.ui.theme.token

import androidx.compose.ui.text.font.FontFamily

// Nếu bác muốn xài font hệ thống thuần túy:
val AppFontFamily = FontFamily.SansSerif

/* Mẹo nhỏ cho Tech Lead: Nếu sau này bác muốn nhét font Custom (.ttf) vào thư mục res/font, 
thì cấu hình chuẩn Spotify sẽ đổi thành như thế này:

val AppFontFamily = FontFamily(
    Font(resId = com.starnest.common.compose.ui.R.font.inter_regular, fontWeight = FontWeight.Normal),
    Font(resId = com.starnest.common.compose.ui.R.font.inter_medium, fontWeight = FontWeight.Medium),
    Font(resId = com.starnest.common.compose.ui.R.font.montserrat_bold, fontWeight = FontWeight.Bold)
)
*/