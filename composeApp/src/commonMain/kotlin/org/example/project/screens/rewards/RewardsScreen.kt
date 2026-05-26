package org.example.project.screens.rewards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.components.DealItemCard
import org.example.project.components.EndOfListFooter
import org.example.project.components.FilterChipsRow
import org.example.project.components.McdBottomNavBar
import org.example.project.components.McdTopAppBar
import org.example.project.components.PromoBannerCard
import org.example.project.components.QrCodeCard
import org.example.project.components.RewardItemCard
import org.example.project.components.SectionHeader
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
                onQrClick = { }
            )
        },
        bottomBar = {
            McdBottomNavBar(
                selectedTab = "Deals",
                onTabSelected = { }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                QrCodeCard(qrCodeId = "M 756 422")
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
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(MockData.rewardItems) { reward ->
                        RewardItemCard(
                            item = reward,
                            modifier = Modifier.width(180.dp)
                        )
                    }
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                                .clickable { onViewAllRewards() }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFC72C)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "View all",
                                    tint = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "View all",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = "Treat yourself",
                    subtitle = "Enjoy even more ways to earn points and get items you love."
                )
                PromoBannerCard()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = "Deals")
                FilterChipsRow(
                    options = listOf("Deals", "McCafe Deals"),
                    selectedOption = selectedFilter,
                    onOptionSelected = { selectedFilter = it }
                )
            }

            items(MockData.dealItems) { deal ->
                DealItemCard(item = deal) {
                    navController.navigate(Screens.StackDetails(it.toString()))
                }
            }

            item {
                EndOfListFooter()
            }
        }
    }
}
