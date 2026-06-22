package com.example.core.navigation.router

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.example.core.navigation.router.home.HomeGraphRouter
import com.example.core.navigation.router.library.LibraryGraphRouter
import com.example.core.navigation.router.search.SearchGraphRouter
import com.example.core.ui.theme.AppTheme
import com.example.resource.R

sealed interface AppIcon {
    @Composable
    fun asPainter(): Painter

    data class ImageVectorIcon(val imageVector: ImageVector) : AppIcon {
        @Composable
        override fun asPainter(): Painter {
            return rememberVectorPainter(image = imageVector)
        }
    }

    data class DrawableResourceIcon(@field:DrawableRes val id: Int) : AppIcon {
        @Composable
        override fun asPainter(): Painter {
            return painterResource(id = id)
        }
    }
}

enum class TopLevelRouter(
    val router: Router,
    @field:StringRes val title: Int,
    val selectedIcon: AppIcon,
    val unselectedIcon: AppIcon
) {
    Home(
        router = HomeGraphRouter,
        title = R.string.home,
        selectedIcon = AppIcon.ImageVectorIcon(Icons.Filled.Home),
        unselectedIcon = AppIcon.ImageVectorIcon(Icons.Outlined.Home)
    ),
    Search(
        router = SearchGraphRouter,
        title = R.string.search,
        selectedIcon = AppIcon.ImageVectorIcon(Icons.Filled.Search),
        unselectedIcon = AppIcon.ImageVectorIcon(Icons.Outlined.Search)
    ),
    Library(
        router = LibraryGraphRouter,
        title = R.string.library,
        selectedIcon = AppIcon.ImageVectorIcon(Icons.Default.LibraryMusic),
        unselectedIcon = AppIcon.ImageVectorIcon(Icons.Outlined.LibraryMusic)
    );

    @Composable
    fun contentColor(isSelected: Boolean): Color {
        return if (isSelected) {
            AppTheme.colorScheme.primary
        } else {
            AppTheme.colorScheme.onBackground
        }
    }

    @Composable
    fun icon(isSelected: Boolean): Painter {
        return if (isSelected) {
            selectedIcon.asPainter()
        } else {
            unselectedIcon.asPainter()
        }
    }
}