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

import androidx.compose.material3.Shapes
import com.example.core.ui.theme.token.*

// --- COMPACT (Phone) ---
val compactShapes = Shapes(
    extraSmall = extraSmallRoundedShape,
    small = smallRoundedShape,
    medium = mediumRoundedShape,
    large = largeRoundedShape,
    extraLarge = extraLargeRoundedShape,
)

// --- MEDIUM (Foldable) ---
val mediumShapes = Shapes(
    extraSmall = extraSmallRoundedShape,
    small = mediumRoundedShape,
    medium = mediumIncreasedRoundedShape,
    large = largeExpandedRoundedShape,
    extraLarge = extraLargeExpandedRoundedShape
)

// --- EXPANDED (Tablet) ---
val expandedShapes = Shapes(
    extraSmall = smallRoundedShape,
    small = mediumRoundedShape,
    medium = extraLargeRoundedShape,
    large = largeExpandedRoundedShape,
    extraLarge = extraLargeExpandedRoundedShape
)