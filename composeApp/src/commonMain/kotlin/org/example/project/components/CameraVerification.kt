package org.example.project.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CameraVerificationButton(
    itemName: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onResult: (Boolean, String) -> Unit
)
