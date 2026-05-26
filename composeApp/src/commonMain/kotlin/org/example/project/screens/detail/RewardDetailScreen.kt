package org.example.project.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.example.project.domain.models.DealItem

/*
 * Created by Gopal Meena on 24/05/26
 */
@Composable
fun RewardDetailScreen(data: DealItem, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailsAppBar(data = data) {
            navController.navigateUp()
        }
        ItemDetails(data)
    }
}

@Composable
fun ItemDetails(item: DealItem) {
    Column {
        AsyncImage(
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Fit,
            model = item.imageUrl
        )
        HorizontalDivider(
            modifier = Modifier.background(Color.Gray)
        )

        Text(
            text = item.title,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = item.title,
            fontSize = 28.sp
        )
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
                color = Color.Black
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