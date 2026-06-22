package com.example.albumdetail.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.core.ui.theme.AppTheme

@Composable
fun AlbumHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    ghostButtonSize: Dp = AppTheme.dimensions.iconExtraLarge
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.paddingMedium),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .size(AppTheme.dimensions.imageSizeExtraLarge)
                .align(Alignment.CenterHorizontally)
                .background(Color.DarkGray)
        )

        Spacer(modifier = Modifier.height(AppTheme.dimensions.spaceExtraLarge))
        Text(
            text = title,
            color = Color.White,
            style = AppTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(AppTheme.dimensions.spaceSmall))

        if (subtitle != null) {
            Text(
                text = subtitle,
                color = Color.LightGray,
                style = AppTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spaceSmall))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.dimensions.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = Color.LightGray,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = Color.LightGray,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimensions.spaceExtraLarge))
            Spacer(modifier = Modifier.size(ghostButtonSize))
        }
    }
}