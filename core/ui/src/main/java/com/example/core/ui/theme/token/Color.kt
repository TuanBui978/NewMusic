package com.example.core.ui.theme.token

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Sắc đỏ (Dùng cho Warning/Error hoặc các nhãn Explicit)
val coral80 = Color(0xFFFFEAEA)
val coral60 = Color(0xFFFF7A7A)
val coral40 = Color(0xFFE91429) // Đỏ cảnh báo của Spotify
val coral30 = Color(0xFFB71C2B)
val Coral20 = Color(0xFF6B000D)

// Dải màu Xanh Spotify (Thay thế Teal để làm Brand)
val teal80 = Color(0xFF1ED760) // Spotify Light Green (Khi hoover/active)
val teal60 = Color(0xFF1DB954) // Spotify Primary Green (Màu gốc)
val teal40 = Color(0xFF1AA34A) // Spotify Dark Green
val teal30 = Color(0xFF16853D)
val teal20 = Color(0xFF0F5C29)

// Sắc vàng (Dùng cho thông báo phụ hoặc Premium badge)
val sunny80 = Color(0xFFFFE6A7)
val sunny60 = Color(0xFFF4D98B)
val sunny40 = Color(0xFFFEE12B) // Vàng sáng
val sunny30 = Color(0xFFB8932C)
val sunny20 = Color(0xFF4A3A00)

// Dải màu trung tính (Thần thái Đen/Xám của Spotify)
val neutral99 = Color(0xFFFFFFFF) // Text chính (Trắng tinh)
val neutral95 = Color(0xFFF6F6F6)
val neutral90 = Color(0xFFEAEAEA) // Text phụ cấp 1
val neutral80 = Color(0xFFB3B3B3) // Text phụ cấp 2 (Màu xám Spotify kinh điển)
val neutral60 = Color(0xFF535353) // Màu phân tách / Component border
val neutral40 = Color(0xFF282828) // Card background / Vùng xám nhạt
val neutral20 = Color(0xFF181818) // Mini Player / Thanh điều hướng dính đáy
val neutral10 = Color(0xFF121212) // Nền đen sâu chủ đạo của toàn app

// Hệ thống màu báo lỗi
val error40 = Color(0xFFE91429)
val error80 = Color(0xFFF2B8B5)
val error20 = Color(0xFF601410)

// Thêm các màu thương hiệu bổ trợ đặc thù app nhạc
val brandYellow = Color(0xFFFEE12B)
val facebookBlue = Color(0xFF1877F2)
val googleRed = Color(0xFFEA4335)
val magicPurple = Color(0xFF8C1DDA) // Tím gradient cho Wrapped/Mood
val magicPink = Color(0xFFE61E80)

// --- 4. Gradient Color Lists (Dữ liệu màu cho Gradient) ---
// Dùng cho Background bài hát/Playlist dựa trên ảnh bìa album
val skyGradientColors = listOf(Color(0xFF2E3033), neutral10)
val sunsetGradientColors = listOf(magicPurple, neutral10)
val magicGradientColors = listOf(teal60, neutral20)
// Token đặc trưng của Spotify
val spotifyGreen = Color(0xFF1DB954)
val spotifyBackground = Color(0xFF121212) // Nền đen tuyền chuẩn Spotify

// Token cho Playlist "Liked Songs" (Tím sang Xanh/Trắng)
val likedSongsStart = Color(0xFF450AF5)
val likedSongsEnd = Color(0xFFC4EFD9)

// Token cho "Your Episodes" (Xanh rêu đậm)
val episodesStart = Color(0xFF006450)
val episodesEnd = Color(0xFF0C3B2E)

// Token cho Premium/Gold (Vàng gold sang tối)
val premiumGoldStart = Color(0xFFF59B23)
val premiumGoldEnd = Color(0xFFE2DA75)

// Token cho các list Blend / Daily Mix (Hồng sang Tím)
val blendStart = Color(0xFFE41D63)
val blendEnd = Color(0xFF463198)

// Danh sách list màu cho Gradient
val likedSongsColors = listOf(likedSongsStart, likedSongsEnd)
val episodesColors = listOf(episodesStart, episodesEnd)
val premiumColors = listOf(premiumGoldStart, premiumGoldEnd)
val blendColors = listOf(blendStart, blendEnd)

// Cực kỳ quan trọng: List màu để làm mờ background từ Album Art xuống nền đen
val backgroundFadeColors = listOf(Color.Transparent, spotifyBackground)
val defaultAlbumStart = Color(0xFF535353) // Xám vừa
val defaultAlbumEnd = Color(0xFF121212)   // Đen nền của Spotify

// Token cho Mood Mix (Tông màu rực rỡ, ví dụ Cam/Hồng đổ bóng sang Tím)
val moodMixStart = Color(0xFFFF6437) // Cam cháy
val moodMixEnd = Color(0xFF8E24AA)   // Tím đậm

// Gắn vào List để ném cho Brush
val defaultAlbumGradientColors = listOf(defaultAlbumStart, defaultAlbumEnd)
val moodMixGradientColors = listOf(moodMixStart, moodMixEnd)