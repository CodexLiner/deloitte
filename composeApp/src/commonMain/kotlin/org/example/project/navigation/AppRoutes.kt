package org.example.project.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object RewardsScreen : Screens

    @Serializable
    data class StackDetails(val data : String) : Screens

    @Serializable
    data object AllRewardsScreen : Screens
}