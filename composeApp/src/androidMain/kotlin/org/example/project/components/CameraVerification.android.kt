package org.example.project.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun CameraVerificationButton(
    itemName: String,
    imageUrl: String,
    modifier: Modifier,
    onResult: (Boolean, String) -> Unit
) {
    // Android implementation not requested or handled differently
    Text("Camera scanning only available on iOS", color = Color.Gray, modifier = modifier)
}
