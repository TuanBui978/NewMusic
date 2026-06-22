package com.example.music.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.navigation.router.Router
import com.example.core.ui.util.ScaffoldState
import kotlin.reflect.KClass


data class ScaffoldStateImpl(
    override val showNavigationBar: State<Boolean>,
) : ScaffoldState

@Composable
fun rememberScaffoldState(
    navController: NavController,
    topLevelRouter: List<KClass<out Router>>
) : ScaffoldState {
    val backStackEntryState = navController.currentBackStackEntryAsState()
    return remember(navController, topLevelRouter) {
        val showNavBarState = derivedStateOf {
                val currentDestination = backStackEntryState.value?.destination
                currentDestination?.let { dest ->
                    topLevelRouter.any { routeClass ->
                        dest.hierarchy.any { it.hasRoute(routeClass) }
                    }
                } ?: false
            }
        ScaffoldStateImpl(showNavigationBar = showNavBarState)
    }
}