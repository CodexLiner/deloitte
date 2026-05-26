package org.example.project.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.example.project.components.CameraVerificationButton
import org.example.project.domain.models.DealItem

@Composable
fun RewardDetailScreen(data: DealItem, navController: NavHostController) {
    var verificationResult by remember { mutableStateOf<String?>(null) }
    var isVerified by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DetailsAppBar(data = data) {
                navController.navigateUp()
            }
        },
        bottomBar = {
            Column {
                if (verificationResult != null) {
                    Surface(
                        color = if (isVerified) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = verificationResult!!,
                            modifier = Modifier.padding(16.dp),
                            color = if (isVerified) Color(0xFF2E7D32) else Color(0xFFC62828),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CameraVerificationButton(
                            itemName = data.title,
                            imageUrl = data.imageUrl,
                            modifier = Modifier.weight(1f),
                            onResult = { success, message ->
                                isVerified = success
                                verificationResult = message
                            }
                        )

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC72C)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Add to Order",
                                color = Color(0xFF27251F),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        ItemDetails(data, paddingValues)
    }
}

@Composable
fun ItemDetails(item: DealItem, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = item.imageUrl
            )
            
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(60.dp),
                color = Color(0xFFDA291C),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = item.priceString,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = item.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF27251F),
                lineHeight = 32.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            HorizontalDivider(color = Color(0xFFEEEEEE))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Product Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF27251F)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Enjoy the delicious ${item.title} at an amazing price. Freshly prepared and served hot. Limited time offer only available on the app.",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (!item.inStock) {
                Surface(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Currently out of stock at your selected restaurant.",
                        color = Color(0xFFE65100),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsAppBar(
    data: DealItem,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = data.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}
