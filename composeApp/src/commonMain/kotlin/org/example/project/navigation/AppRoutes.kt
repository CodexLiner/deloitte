package org.example.project.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object RewardsScreen : Screens

    @Serializable
    data object AllRewardsScreen : Screens
}