package com.example.albumdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.albumdetail.ui.local.LocalAlbumDetailScreen
import com.example.albumdetail.ui.standard.AlbumDetailScreen
import com.example.core.navigation.CommonDestinationRegister
import com.example.core.navigation.router.album.AlbumDetailRoute
import com.example.core.navigation.router.album.LocalAlbumDetailRoute
import jakarta.inject.Inject

class AlbumDetailGraphRegister @Inject constructor() : CommonDestinationRegister {
    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        composable<AlbumDetailRoute> {
            AlbumDetailScreen(navController = navController)
        }
        composable<LocalAlbumDetailRoute> {
            LocalAlbumDetailScreen(navController = navController)
        }
    }
}