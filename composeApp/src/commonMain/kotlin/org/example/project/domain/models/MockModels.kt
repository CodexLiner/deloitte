package org.example.project.domain.models

data class RewardItem(
    val id: String,
    val name: String,
    val pointsRequired: Int,
    val imageUrl: String,
    val isLocked: Boolean = true,
    val isAvailable: Boolean = true
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
            name = "Hash Brown",
            pointsRequired = 2500,
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Hash-Browns-1:1-1-product-tile-desktop",
            isAvailable = false
        ),
        RewardItem(
            id = "3",
            name = "Any Size Soft Drink",
            pointsRequired = 3200,
            imageUrl = "https://pngimg.com/uploads/cocacola/cocacola_PNG4.png"
        ),
        RewardItem(
            id = "4",
            name = "Hot Apple Pie",
            pointsRequired = 3800,
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Apple-Pie-1:1-1-product-tile-desktop"
        ),
        RewardItem(
            id = "5",
            name = "Any Size McCafé Hot Beverage",
            pointsRequired = 4200,
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-McCafe-Latte-1:1-1-product-tile-desktop"
        ),
        RewardItem(
            id = "6",
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
