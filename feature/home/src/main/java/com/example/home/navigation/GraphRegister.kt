package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.GraphRegister
import com.example.core.navigation.CommonDestinationRegister
import com.example.core.navigation.router.home.HomeGraphRouter
import com.example.home.ui.HomeScreen
import jakarta.inject.Inject

class HomeGraphRegister @Inject constructor(
    private val commonDestinationRegisters: Set<@JvmSuppressWildcards CommonDestinationRegister>
) : GraphRegister {
    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        navigation<HomeGraphRouter>(startDestination = HomeScreen) {
            composable<HomeScreen> {
                HomeScreen()
            }
            commonDestinationRegisters.forEach { register ->
                with(register) {
                    registerGraph(navController)
                }
            }
        }
    }
}