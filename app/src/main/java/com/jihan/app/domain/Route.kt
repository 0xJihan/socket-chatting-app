package com.jihan.app.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {


    @Serializable
    data object Login : Route
    @Serializable
    data object Signup : Route
    @Serializable
    data object Home : Route
    data object Main : Route


}