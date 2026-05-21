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
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-oreo-mcflurry-1:1-1-product-tile-desktop"
        ),
        RewardItem(
            id = "2",
            name = "Any Size Soft Drink",
            pointsRequired = 3200,
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Coca-Cola-Classic-Small-1:1-1-product-tile-desktop"
        )
    )

    val dealItems = listOf(
        DealItem(
            id = "d1",
            title = "Dunked K-BBQ McCrispy Small Combo & 3pc Chicken McNuggets",
            priceString = "$18",
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/DC_202402_0052_MediumDunkedKBBQMcCrispyCombo_1564x1564-1:1-1-product-tile-desktop"
        ),
        DealItem(
            id = "d2",
            title = "Double Stack",
            priceString = "$18",
            imageUrl = "https://s7d1.scene7.com/is/image/mcdonalds/DC_202302_8805_DoubleCheeseburger_1564x1564-1:1-1-product-tile-desktop"
        )
    )
}
