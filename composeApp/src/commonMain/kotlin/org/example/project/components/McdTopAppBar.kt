package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun McdTopAppBar(
    points: Int = 0,
    showQrIcon: Boolean = true,
    onQrClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "My",
                    color = Color(0xFFDA291C),
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
                Text(
                    text = "M",
                    color = Color(0xFFFFC72C),
                    fontWeight = FontWeight.Black,
                    fontSize = 32.sp
                )
            }
        },
        navigationIcon = {
            if (showQrIcon) {
                IconButton(onClick = onQrClick, modifier = Modifier.padding(start = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFFFC72C), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = "QR Code",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$points",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color(0xFF27251F),
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "pts",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        lineHeight = 10.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Points Details",
                    tint = Color(0xFF27251F),
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        )
    )
}
