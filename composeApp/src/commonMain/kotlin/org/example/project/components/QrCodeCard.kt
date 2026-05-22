package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun QrCodeCard(
    qrCodeId: String = "M 756 422"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC72C)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = Color(0xFF27251F),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .padding(4.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        AsyncImage(
                            model = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=$qrCodeId",
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .size(110.dp)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = qrCodeId,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Use code to collect points.",
                    color = Color(0xFF27251F),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
