package com.nimain.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavGraphProvider {
    fun register(builder: NavGraphBuilder, navController: NavController)
}