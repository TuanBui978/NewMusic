package com.example.music.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.GraphRegister
import com.example.core.navigation.router.splash.SplashGraphRouter
import com.example.core.navigation.router.TopLevelRouter
import com.example.core.ui.component.bottomsheet.FullScreenBottomSheet
import com.example.core.ui.component.bottomsheet.rememberFullScreenBottomSheetState
import com.example.core.ui.component.mediaplayer.MiniMediaPlayer
import com.example.core.ui.theme.AppTheme
import com.example.music.ui.model.rememberScaffoldState
import com.example.playing.ui.PlayingBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var graphRegister: Set<@JvmSuppressWildcards GraphRegister>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenLayout {
                graphRegister.forEach { register ->
                    with(register) {
                        registerGraph(navController = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScreenLayout(
    builderBlock: NavGraphBuilder.(navController: NavHostController) -> Unit
) {
    val topLevelRouter = TopLevelRouter.entries
    val navController = rememberNavController()
    val topRouterClasses = remember(topLevelRouter) {
        topLevelRouter.map { it.router::class }
    }
    val scaffoldState = rememberScaffoldState(
        navController = navController,
        topLevelRouter = topRouterClasses
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarVisible by scaffoldState.showNavigationBar
    AppTheme {
        Scaffold(
            bottomBar = {
                TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                AnimatedVisibility(
                    visible = bottomBarVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    val appBarColor = AppTheme.extraGradient.backgroundFadeGradient
                    NavigationBar(
                        modifier = Modifier.drawBehind {
                            drawRect(
                                brush = appBarColor
                            )
                        },
                        containerColor = Color.Transparent,
                    ) {
                        topLevelRouter.forEach { topLevelRouter ->
                            val isSelected = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(topLevelRouter.router::class) } == true
                            NavigationBarItem(
                                selected = isSelected,
                                icon = {
                                    Icon(
                                        painter = topLevelRouter.icon(isSelected),
                                        contentDescription = null,
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(topLevelRouter.title),
                                    )
                                },
                                onClick = {
                                    navController.navigate(topLevelRouter.router) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = topLevelRouter.contentColor(isSelected),
                                    selectedTextColor = topLevelRouter.contentColor(isSelected),
                                )
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            val layoutDirection = LocalLayoutDirection.current
            val customPadding = PaddingValues(
                start = paddingValues.calculateStartPadding(layoutDirection),
                top = 0.dp,
                end = paddingValues.calculateEndPadding(layoutDirection),
                bottom = paddingValues.calculateBottomPadding()
            )
            NavHost(
                modifier = Modifier
                    .padding(customPadding),
                navController = navController,
                startDestination = SplashGraphRouter
            ) {
                builderBlock(navController)
            }
            if (bottomBarVisible) {
                PlayingBottomSheet(
                    modifier = Modifier.padding(customPadding)
                )
            }
        }
    }
}