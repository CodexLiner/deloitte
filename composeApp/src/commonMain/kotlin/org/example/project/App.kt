package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.json.Json
import org.example.project.domain.models.DealItem
import org.example.project.navigation.Screens
import org.example.project.screens.detail.RewardDetailScreen
import org.example.project.screens.rewards.AllRewardsScreen
import org.example.project.screens.rewards.RewardsScreen

@Composable
fun ProductBrowserApp() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screens.RewardsScreen
        ) {
            composable<Screens.RewardsScreen> {
                RewardsScreen(navController = navController, onViewAllRewards = {
                    navController.navigate(Screens.AllRewardsScreen)
                })
            }
            composable<Screens.AllRewardsScreen> {
                AllRewardsScreen(onBack = {
                    navController.popBackStack()
                })
            }

            composable<Screens.StackDetails> {
                val data = it.toRoute<Screens.StackDetails>().data
                val dealItem = Json.decodeFromString<DealItem>(data)
                RewardDetailScreen(dealItem, navController)
            }
        }
    }
}
