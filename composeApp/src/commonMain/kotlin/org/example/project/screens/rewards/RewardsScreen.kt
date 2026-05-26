package org.example.project.screens.rewards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.components.*
import org.example.project.domain.models.MockData
import org.example.project.navigation.Screens

@Composable
fun RewardsScreen(
    onViewAllRewards: () -> Unit = {},
    navController: NavController
) {
    var selectedFilter by remember { mutableStateOf("Deals") }
    val listState = rememberLazyListState()
    
    val showQrInTopBar by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        topBar = {
            McdTopAppBar(
                points = 0,
                showQrIcon = showQrInTopBar,
                onQrClick = { },
                onPointsClick = { }
            )
        },
        bottomBar = {
            McdBottomNavBar(
                selectedTab = "Deals",
                onTabSelected = { }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                QrCodeCard(qrCodeId = "M 431 01")
            }

            item {
                SectionHeader(
                    title = "Rewards",
                    actionText = "View all",
                    onActionClick = onViewAllRewards
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(MockData.rewardItems) { reward ->
                        RewardItemCard(item = reward)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(title = "Deals")
                FilterChipsRow(
                    options = listOf("Deals", "McCafe Deals"),
                    selectedOption = selectedFilter,
                    onOptionSelected = { selectedFilter = it }
                )
            }

            items(MockData.dealItems) { deal ->
                DealItemCard(item = deal) {
                    val dealJson = Json.encodeToString(it)
                    navController.navigate(Screens.StackDetails(dealJson))
                }
            }

            item {
                EndOfListFooter()
            }
        }
    }
}
