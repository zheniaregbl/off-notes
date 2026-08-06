package com.nimain.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nimain.core.navigation.route.HomeRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Any = HomeRoute,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeInWithScale() },
        exitTransition = { fadeOutWithScale() },
        popEnterTransition = { fadeInWithScale() },
        popExitTransition = { fadeOutWithScale() },
        builder = builder
    )
}