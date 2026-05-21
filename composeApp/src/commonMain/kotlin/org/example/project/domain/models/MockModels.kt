package org.example.project.domain.models

data class RewardItem(
    val id: String,
    val name: String,
    val pointsRequired: Int,
    val imageUrl: String,
    val isLocked: Boolean = true
)

data class DealItem(
    val id: String,
    val title: String,
    val priceString: String,
    val imageUrl: String
)

object MockData {
    val rewardItems = listOf(
        RewardItem(
            id = "1",
            name = "McFlurry",
            pointsRequired = 6800,
            imageUrl = "https://pngimg.com/uploads/ice_cream/ice_cream_PNG5101.png"
        ),
        RewardItem(
            id = "2",
            name = "Coca Cola",
            pointsRequired = 3200,
            imageUrl = "https://pngimg.com/uploads/cocacola/cocacola_PNG4.png"
        ),
        RewardItem(
            id = "3",
            name = "Large Fries",
            pointsRequired = 4500,
            imageUrl = "https://mcdonalds.com.lb/storage/menu-products/June2025/F6arpVLuIRcUflW6cXY9.png"
        )
    )

    val dealItems = listOf(
        DealItem(
            id = "d1",
            title = "Dunked K-BBQ McCrispy Small Combo & 3pc Chicken McNuggets",
            priceString = "$18",
            imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800"
        ),
        DealItem(
            id = "d2",
            title = "Double Stack",
            priceString = "$18",
            imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=800"
        ),
        DealItem(
            id = "d3",
            title = "Big Mac Meal",
            priceString = "$22",
            imageUrl = "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800"
        ),
        DealItem(
            id = "d4",
            title = "Spicy McCrispy",
            priceString = "$20",
            imageUrl = "https://images.unsplash.com/photo-1606755962773-d324e0a13086?w=800"
        )
    )}
