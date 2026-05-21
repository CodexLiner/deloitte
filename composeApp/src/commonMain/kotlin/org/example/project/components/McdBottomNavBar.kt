package org.example.project.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun McdBottomNavBar(
    selectedTab: String = "Deals",
    onTabSelected: (String) -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF27251F)
    ) {
        val tabs = listOf(
            "Home" to Icons.Default.Home,
            "Deals" to Icons.Default.LocalOffer,
            "Order" to Icons.Default.RestaurantMenu,
            "Feedback" to Icons.Default.ChatBubbleOutline,
            "More" to Icons.Default.MoreHoriz
        )

        tabs.forEach { (label, icon) ->
            val isSelected = label == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(label) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(text = label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = if (isSelected) Color(0xFFFFC72C) else Color.DarkGray, // Yellow if selected
                    unselectedIconColor = Color.DarkGray,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
