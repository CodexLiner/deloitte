package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun McdBottomNavBar(
    selectedTab: String = "Deals",
    onTabSelected: (String) -> Unit = {}
) {
    Surface(
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.height(72.dp)
        ) {
            NavigationBarItem(
                selected = selectedTab == "Home",
                onClick = { onTabSelected("Home") },
                icon = { Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
                label = { Text("Home", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = navigationItemColors()
            )

            NavigationBarItem(
                selected = selectedTab == "Deals",
                onClick = { onTabSelected("Deals") },
                icon = {
                    ArchesIcon(isSelected = selectedTab == "Deals")
                },
                label = { Text("Deals", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = navigationItemColors()
            )

            NavigationBarItem(
                selected = selectedTab == "Order",
                onClick = { onTabSelected("Order") },
                icon = { Icon(Icons.Outlined.RestaurantMenu, contentDescription = null, modifier = Modifier.size(24.dp)) },
                label = { Text("Order", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = navigationItemColors()
            )

            NavigationBarItem(
                selected = selectedTab == "Feedback",
                onClick = { onTabSelected("Feedback") },
                icon = { Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(24.dp)) },
                label = { Text("Feedback", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = navigationItemColors()
            )

            NavigationBarItem(
                selected = selectedTab == "More",
                onClick = { onTabSelected("More") },
                icon = { Icon(Icons.Outlined.MoreHoriz, contentDescription = null, modifier = Modifier.size(24.dp)) },
                label = { Text("More", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = navigationItemColors()
            )
        }
    }
}

@Composable
fun navigationItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color(0xFFDA291C),
    unselectedIconColor = Color.Gray,
    selectedTextColor = Color(0xFF27251F),
    unselectedTextColor = Color.Gray,
    indicatorColor = Color.Transparent
)

@Composable
fun ArchesIcon(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .let {
                if (isSelected) {
                    it.border(1.5.dp, Color(0xFFFFC72C), CircleShape).padding(2.dp)
                } else it
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "M",
                color = if (isSelected) Color(0xFFDA291C) else Color.Gray,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                lineHeight = 18.sp
            )
        }
    }
}
