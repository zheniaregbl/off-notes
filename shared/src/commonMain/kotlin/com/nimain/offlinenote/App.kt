package com.nimain.offlinenote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nimain.core.navigation.AppNavHost
import com.nimain.core.navigation.route.HomeRoute
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.offlinenote.navigation.NavGraphRegistry
import org.koin.compose.koinInject

@Composable
fun App() {
    val navController = rememberNavController()
    val registry = koinInject<NavGraphRegistry>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        AppNavHost(
            navController = navController,
            startDestination = HomeRoute
        ) { registry.registerAll(this, navController) }
    }
}