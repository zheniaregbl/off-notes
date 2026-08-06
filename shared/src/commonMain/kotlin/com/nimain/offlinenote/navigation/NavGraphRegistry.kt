package com.nimain.offlinenote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nimain.core.navigation.NavGraphProvider

class NavGraphRegistry(private val providers: List<NavGraphProvider>) {
    fun registerAll(
        builder: NavGraphBuilder,
        navController: NavController
    ) {
        println("providers : ${providers.size}")
        providers.forEach { it.register(builder, navController) }
    }
}