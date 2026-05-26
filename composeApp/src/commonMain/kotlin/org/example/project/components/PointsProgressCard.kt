package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PointsProgressCard(
    currentPoints: Int = 0,
    modifier: Modifier = Modifier
) {
    val tiers = listOf(2000, 4000, 6000, 8000)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = "$currentPoints",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF27251F)
                )
                Text(
                    text = "Total Points",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
            
            Text(
                text = "How it works",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007A9A),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Progress Bar with Tiers
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFFEEEEEE), RoundedCornerShape(4.dp))
        ) {
            val progress = (currentPoints.toFloat() / tiers.last().toFloat()).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(Color(0xFFDA291C), RoundedCornerShape(4.dp))
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tiers.forEach { tier ->
                Text(
                    text = "${tier / 1000}K",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentPoints >= tier) Color.Black else Color.Gray
                )
            }
        }
    }
}
