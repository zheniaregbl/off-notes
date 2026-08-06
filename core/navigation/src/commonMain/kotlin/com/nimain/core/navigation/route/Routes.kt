package com.nimain.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable data object HomeRoute
@Serializable data class NoteRoute(val noteId: String? = null)