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

import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.core.ui.theme.token.*

val LocalExtraColor = staticCompositionLocalOf<ExtraColor> { error("No extra color provided") }
val LocalExtraGradient = staticCompositionLocalOf<ExtraGradient> { error("No extra gradient provided") }

@Immutable
class ExtraColor(
    val brandYellow: Color,
    val facebookBlue: Color,
    val googleRed: Color,
    val magicPurple: Color,
    val magicPink: Color,
    val screenSettingsBg: Color
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtraColor

        if (brandYellow != other.brandYellow) return false
        if (facebookBlue != other.facebookBlue) return false
        if (googleRed != other.googleRed) return false
        if (magicPurple != other.magicPurple) return false
        if (magicPink != other.magicPink) return false
        if (screenSettingsBg != other.screenSettingsBg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = brandYellow.hashCode()
        result = 31 * result + facebookBlue.hashCode()
        result = 31 * result + googleRed.hashCode()
        result = 31 * result + magicPurple.hashCode()
        result = 31 * result + magicPink.hashCode()
        result = 31 * result + screenSettingsBg.hashCode()
        return result
    }
}

val extraColor = ExtraColor(
    brandYellow = brandYellow,
    facebookBlue = facebookBlue,
    googleRed = googleRed,
    magicPurple = magicPurple,
    magicPink = magicPink,
    screenSettingsBg = neutral10
)

@Immutable
class ExtraGradient(
    val defaultAlbumGradient: Brush,
    val moodMixGradient: Brush,
    val likedSongsGradient: Brush,      // Tim bài hát yêu thích
    val episodesGradient: Brush,        // Podcast của bạn
    val premiumGradient: Brush,         // Nền quảng cáo Premium
    val blendGradient: Brush,           // Nền Daily Mix / Blend
    val backgroundFadeGradient: Brush   // Dùng để đè lên Album cover, fade dần xuống màu nền
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtraGradient

        if (defaultAlbumGradient != other.defaultAlbumGradient) return false
        if (moodMixGradient != other.moodMixGradient) return false
        if (likedSongsGradient != other.likedSongsGradient) return false
        if (episodesGradient != other.episodesGradient) return false
        if (premiumGradient != other.premiumGradient) return false
        if (blendGradient != other.blendGradient) return false
        if (backgroundFadeGradient != other.backgroundFadeGradient) return false

        return true
    }

    override fun hashCode(): Int {
        var result = defaultAlbumGradient.hashCode()
        result = 31 * result + moodMixGradient.hashCode()
        result = 31 * result + likedSongsGradient.hashCode()
        result = 31 * result + episodesGradient.hashCode()
        result = 31 * result + premiumGradient.hashCode()
        result = 31 * result + blendGradient.hashCode()
        result = 31 * result + backgroundFadeGradient.hashCode()
        return result
    }
}

// ... (Giữ nguyên val extraColor của bác) ...

val extraGradient = ExtraGradient(
    defaultAlbumGradient = Brush.linearGradient(
        colors = defaultAlbumGradientColors
    ),
    moodMixGradient = Brush.verticalGradient(
        colors = moodMixGradientColors
    ),
    likedSongsGradient = Brush.linearGradient(
        colors = likedSongsColors
    ),
    episodesGradient = Brush.linearGradient(
        colors = episodesColors
    ),
    premiumGradient = Brush.linearGradient(
        colors = premiumColors
    ),
    blendGradient = Brush.verticalGradient(
        colors = blendColors
    ),
    backgroundFadeGradient = Brush.verticalGradient(
        colors = backgroundFadeColors
    )
)