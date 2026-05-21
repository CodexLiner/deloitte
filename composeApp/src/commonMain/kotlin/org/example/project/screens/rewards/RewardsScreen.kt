package org.example.project.screens.rewards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.components.DealItemCard
import org.example.project.components.EndOfListFooter
import org.example.project.components.FilterChipsRow
import org.example.project.components.McdTopAppBar
import org.example.project.components.PromoBannerCard
import org.example.project.components.QrCodeCard
import org.example.project.components.RewardItemCard
import org.example.project.components.SectionHeader
import org.example.project.domain.models.MockData

@Composable
fun RewardsScreen() {
    var selectedFilter by remember { mutableStateOf("Deals") }

    Scaffold(
        topBar = { McdTopAppBar(points = 0) },
        containerColor = Color(0xFFF1F1F1) // Light gray background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // QR Code Card
            item {
                QrCodeCard(qrCodeId = "M 756 422")
            }

            // Rewards Section
            item {
                SectionHeader(
                    title = "Rewards",
                    actionText = "View all",
                    onActionClick = { /* TODO */ }
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

            // Treat Yourself Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = "Treat yourself",
                    subtitle = "Enjoy even more ways to earn points and get items you love."
                )
                PromoBannerCard()
            }

            // Deals Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = "Deals")
                FilterChipsRow(
                    options = listOf("Deals", "McCafe Deals"),
                    selectedOption = selectedFilter,
                    onOptionSelected = { selectedFilter = it }
                )
            }

            // Deal Items List
            items(MockData.dealItems) { deal ->
                DealItemCard(item = deal)
            }

            // McDelivery Promo
            item {
                PromoBannerCard(
                    title = "Spend \$35 and get a FREE Quarter Pounder Medium Combo"
                )
            }
            
            // Footer
            item {
                EndOfListFooter()
            }
        }
    }
}
