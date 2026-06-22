/**
 * Copyright (c) 2026.
 * Author: TuanBuiQuang
 * Module Owner: Android Core Team
 * Reviewer: Tech Lead
 */
package com.example.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import com.example.core.ui.theme.token.*

val LocalDimens = staticCompositionLocalOf<Dimens> {
    error("CompositionLocal LocalDimens not present")
}

@Immutable
class Dimens(
    // 1. Spacing & Margin
    val spaceExtraSmall: Dp,
    val spaceSmall: Dp,
    val spaceMedium: Dp,
    val spaceLarge: Dp,
    val spaceExtraLarge: Dp,

    // 2. Padding (Đặc biệt quan trọng cho lề màn hình)
    val paddingSmall: Dp,
    val paddingMedium: Dp, // Mặc định dùng làm lề trái/phải toàn app
    val paddingLarge: Dp,

    // 3. Icons
    val iconMedium: Dp,
    val iconLarge: Dp,
    val iconExtraLarge: Dp, // Dùng cho Play Button lơ lửng

    // 4. Images
    val imageSizeSmall: Dp,
    val imageSizeMedium: Dp,
    val imageSizeLarge: Dp,
    val imageSizeExtraLarge: Dp,

    // 5. Interactive
    val buttonHeight: Dp,

    // 6. Shapes/Radius
    val radiusSmall: Dp,
    val radiusMedium: Dp,
    val radiusLarge: Dp,

    // 7. App Specific
    val miniPlayerHeight: Dp
)

// ==========================================
// MAPPING COMPACT (PHONE)
// ==========================================
val compactDimens = Dimens(
    spaceExtraSmall = compactSizeXS,
    spaceSmall = compactSizeS,
    spaceMedium = compactSizeM,
    spaceLarge = compactSizeL,
    spaceExtraLarge = compactSizeXL,

    paddingSmall = compactSizeS,
    paddingMedium = compactSizeL, // 16.dp
    paddingLarge = compactSizeXL,

    iconMedium = compactIconM,
    iconLarge = compactIconL,
    iconExtraLarge = compactIconXL,

    imageSizeSmall = compactImageSizeS,
    imageSizeMedium = compactImageSizeM,
    imageSizeLarge = compactImageSizeL,
    imageSizeExtraLarge = compactImageSizeXL,

    buttonHeight = compactButtonHeight,

    radiusSmall = compactRadiusS,
    radiusMedium = compactRadiusM,
    radiusLarge = compactRadiusL,

    miniPlayerHeight = spotifyMiniPlayerHeightCompact
)

// ==========================================
// MAPPING MEDIUM (FOLDABLE / SMALL TABLET)
// ==========================================
val mediumDimens = Dimens(
    spaceExtraSmall = mediumSizeXS,
    spaceSmall = mediumSizeS,
    spaceMedium = mediumSizeM,
    spaceLarge = mediumSizeL,
    spaceExtraLarge = mediumSizeXL,

    paddingSmall = mediumSizeS,
    paddingMedium = mediumSizeL, // 20.dp
    paddingLarge = mediumSizeXL,

    iconMedium = mediumIconM,
    iconLarge = mediumIconL,
    iconExtraLarge = mediumIconXL,

    imageSizeSmall = mediumImageSizeS,
    imageSizeMedium = mediumImageSizeM,
    imageSizeLarge = mediumImageSizeL,
    imageSizeExtraLarge = mediumImageSizeXL,

    buttonHeight = mediumButtonHeight,

    radiusSmall = mediumRadiusS,
    radiusMedium = mediumRadiusM,
    radiusLarge = mediumRadiusL,

    miniPlayerHeight = spotifyMiniPlayerHeightMedium
)

// ==========================================
// MAPPING EXPANDED (TABLET / DESKTOP)
// ==========================================
val expandedDimens = Dimens(
    spaceExtraSmall = expandedSizeXS,
    spaceSmall = expandedSizeS,
    spaceMedium = expandedSizeM,
    spaceLarge = expandedSizeL,
    spaceExtraLarge = expandedSizeXL,

    paddingSmall = expandedSizeS,
    paddingMedium = expandedSizeL, // 24.dp
    paddingLarge = expandedSizeXL,

    iconMedium = expandedIconM,
    iconLarge = expandedIconL,
    iconExtraLarge = expandedIconXL,

    imageSizeSmall = expandedImageSizeS,
    imageSizeMedium = expandedImageSizeM,
    imageSizeLarge = expandedImageSizeL,
    imageSizeExtraLarge = expandedImageSizeXL,

    buttonHeight = expandedButtonHeight,

    radiusSmall = expandedRadiusS,
    radiusMedium = expandedRadiusM,
    radiusLarge = expandedRadiusL,

    miniPlayerHeight = spotifyMiniPlayerHeightExpanded
)