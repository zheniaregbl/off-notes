package com.nimain.note.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nimain.core.navigation.NavGraphProvider
import com.nimain.core.navigation.fadeInWithScale
import com.nimain.core.navigation.fadeOutWithScale
import com.nimain.core.navigation.route.HomeRoute
import com.nimain.core.navigation.route.NoteRoute
import com.nimain.core.navigation.slideDownModal
import com.nimain.core.navigation.slideUpModal
import com.nimain.note.presentation.NoteScreen

class NoteNavGraphProvider : NavGraphProvider {
    override fun register(
        builder: NavGraphBuilder,
        navController: NavController
    ) {
        builder.composable<NoteRoute>(
            enterTransition = { slideUpModal() },
            exitTransition = { fadeOutWithScale() },
            popEnterTransition = { fadeInWithScale() },
            popExitTransition = { slideDownModal() }
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<NoteRoute>()
            NoteScreen(
                noteId = route.noteId,
                onConfirm = { navController.popBackStack(HomeRoute, inclusive = false) },
                onBack = { navController.popBackStack(HomeRoute, inclusive = false) }
            )
        }
    }
}