/**
 * Copyright (c) 2026.
 *
 * Author: TuanBuiQuang
 * Created: 4/5/26
 *
 * Module Owner: Android Core Team
 * Reviewer: Tech Lead
 */

package com.example.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * A contract for registering top-level navigation graphs within the application.
 * Implementations of this interface are typically collected via Dependency Injection (e.g., Hilt Multibinding)
 * to populate the root [androidx.navigation.compose.NavHost].
 */
interface GraphRegister {
    /**
     * Registers the destinations associated with this root-level graph.
     *
     * @param navController The primary [NavController] used for app-wide, full-screen navigation
     * (e.g., navigating from Splash to Main, or opening a global screen that overlays everything).
     */
    fun NavGraphBuilder.registerGraph(
        navController: NavController,
    )
}


/**
 * A contract for registering nested navigation graphs, typically used within a localized scope
 * such as a Bottom Navigation Bar or a specific sub-flow.
 * Implementations of this interface populate a nested [androidx.navigation.compose.NavHost].
 */
interface CommonDestinationRegister {
    /**
     * Registers the destinations associated with this nested graph.
     *
     * @param navController The primary [NavController] passed down from the root scope.
     * Provided here to allow inner screens to execute global navigation commands
     * (e.g., escaping the nested scope to open a full-screen setting or detail page).
     */
    fun NavGraphBuilder.registerGraph(
        navController: NavController
    )
}