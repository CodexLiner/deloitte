package org.example.project.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun EndOfListFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tag Image (mocked)
        AsyncImage(
            model = "https://s7d1.scene7.com/is/image/mcdonalds/reward_tag_icon", // Placeholder
            contentDescription = "Tag",
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "That's all for now.",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = Color(0xFF27251F)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Check in again soon for more deliciousness!",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier.size(32.dp),
                tint = Color.DarkGray
            )
        }
    }
}
