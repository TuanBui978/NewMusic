package com.example.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.GraphRegister
import com.example.core.navigation.router.splash.SplashGraphRouter
import com.example.splash.ui.SplashScreen
import jakarta.inject.Inject

class SplashGraphRegister @Inject constructor() : GraphRegister {
    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        navigation<SplashGraphRouter>(startDestination = SplashScreen) {
            composable<SplashScreen> {
                SplashScreen(
                    navController = navController
                )
            }
        }
    }
}