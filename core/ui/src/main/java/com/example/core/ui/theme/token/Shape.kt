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

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val extraSmallRoundedShape = RoundedCornerShape(4.dp) // Tag Explicit, Nhãn phụ
val smallRoundedShape = RoundedCornerShape(8.dp)      // Ảnh bìa Album, Card Bài hát
val mediumRoundedShape = RoundedCornerShape(12.dp)
val largeRoundedShape = RoundedCornerShape(16.dp)     // Góc trên của Bottom Sheet Player
val extraLargeRoundedShape = RoundedCornerShape(28.dp)

val mediumIncreasedRoundedShape = RoundedCornerShape(20.dp)
val largeExpandedRoundedShape = RoundedCornerShape(32.dp)
val extraLargeExpandedRoundedShape = RoundedCornerShape(40.dp)

// Tròn tuyệt đối cho Nút bấm Play, Avatar Ca sĩ, Bong bóng thể loại
val circleCcShape = CircleShape