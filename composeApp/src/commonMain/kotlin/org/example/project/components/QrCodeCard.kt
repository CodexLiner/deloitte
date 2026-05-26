package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Scan at Kiosk",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = Color(0xFF27251F)
                )
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = Color(0xFF27251F),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=$qrCodeId",
                        contentDescription = "QR Code",
                        modifier = Modifier.size(140.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = qrCodeId,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.Black,
                        letterSpacing = 2.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Points will be added to your account when you scan your code.",
                color = Color(0xFF27251F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}
