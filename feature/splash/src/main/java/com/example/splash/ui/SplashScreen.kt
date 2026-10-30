package com.example.splash.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.navigation.router.home.HomeGraphRouter
import com.example.core.navigation.router.splash.SplashGraphRouter
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
object SplashScreen

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(HomeGraphRouter) {
            popUpTo(SplashGraphRouter) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Splash Screen")
    }
}