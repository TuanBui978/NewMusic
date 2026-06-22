package com.example.library.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.GraphRegister
import com.example.core.navigation.CommonDestinationRegister
import com.example.core.navigation.router.library.LibraryGraphRouter
import com.example.library.ui.LibraryScreen
import jakarta.inject.Inject

class LibraryGraphRegister @Inject constructor(
    private val commonDestinationRegisters: Set<@JvmSuppressWildcards CommonDestinationRegister>
) : GraphRegister {
    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        navigation<LibraryGraphRouter>(startDestination = LibraryScreen) {
            composable<LibraryScreen> {
                LibraryScreen(
                    navController = navController
                )
            }
            commonDestinationRegisters.forEach { register ->
                with(register) {
                    registerGraph(navController)
                }
            }
        }
    }
}