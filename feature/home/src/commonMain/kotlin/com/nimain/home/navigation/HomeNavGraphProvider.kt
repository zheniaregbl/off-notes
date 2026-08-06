package com.nimain.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nimain.core.navigation.NavGraphProvider
import com.nimain.core.navigation.route.HomeRoute
import com.nimain.core.navigation.route.NoteRoute
import com.nimain.home.presentation.HomeScreen

class HomeNavGraphProvider : NavGraphProvider {
    override fun register(
        builder: NavGraphBuilder,
        navController: NavController
    ) {
        builder.composable<HomeRoute> {
            HomeScreen(onNoteClick = { id -> navController.navigate(NoteRoute(id)) })
        }
    }
}