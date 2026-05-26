package org.example.project.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.uikit.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.*
import platform.Vision.*
import platform.darwin.NSObject

private val GenericLabels = setOf(
    "food",
    "dish",
    "cuisine",
    "meal",
    "fast food",
    "produce",
    "ingredient",
    "snack",
    "junk food",
    "prepared food",
    "recipe",
    "tableware",
    "plate",
    "bread",
    "bun",
    "baked goods",
    "processed food",
    "nutrition",
    "appetizer",
    "side dish",
    "delicacy",
    "meat"
)

@Composable
actual fun CameraVerificationButton(
    itemName: String,
    imageUrl: String,
    modifier: Modifier,
    onResult: (Boolean, String) -> Unit,
) {
    val viewController = LocalUIViewController.current
    val coroutineScope = rememberCoroutineScope()

    var isProcessing by remember { mutableStateOf(false) }
    var pickerDelegate by remember { mutableStateOf<NSObject?>(null) }

    OutlinedButton(
        onClick = {
            openCamera(
                viewController = viewController,
                onImageCaptured = { image ->
                    isProcessing = true
                    coroutineScope.launch {
                        val result = runCatching {
                            compareImages(image, imageUrl)
                        }.getOrElse {
                            false to "Comparison failed: ${it.message}"
                        }

                        onResult(result.first, result.second)
                        isProcessing = false
                    }
                },
                onError = { message ->
                    onResult(false, message)
                },
                onDelegateCreated = { delegate ->
                    pickerDelegate = delegate
                },
                onDismiss = {
                    pickerDelegate = null
                }
            )
        },
        modifier = modifier.height(56.dp),
        enabled = !isProcessing,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = if (isProcessing) Color.LightGray else Color(0xFF27251F)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF27251F),
            disabledContentColor = Color.LightGray
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        if (isProcessing) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color(0xFFDA291C),
                strokeWidth = 2.dp
            )
        } else {
            VerifyButtonContent()
        }
    }
}

@Composable
private fun VerifyButtonContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Verify Item",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun openCamera(
    viewController: UIViewController,
    onImageCaptured: (UIImage) -> Unit,
    onError: (String) -> Unit,
    onDelegateCreated: (NSObject) -> Unit,
    onDismiss: () -> Unit,
) {
    val isCameraAvailable = UIImagePickerController.isSourceTypeAvailable(
        UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
    )

    if (!isCameraAvailable) {
        onError("Camera not available")
        return
    }

    val picker = UIImagePickerController().apply {
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
    }

    val delegate = object : NSObject(),
        UIImagePickerControllerDelegateProtocol,
        UINavigationControllerDelegateProtocol {

        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>
        ) {
            val image =
                didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage

            picker.dismissViewControllerAnimated(true, null)
            onDismiss()

            if (image != null) {
                onImageCaptured(image)
            } else {
                onError("No image captured")
            }
        }

        override fun imagePickerControllerDidCancel(
            picker: UIImagePickerController
        ) {
            picker.dismissViewControllerAnimated(true, null)
            onDismiss()
        }
    }

    onDelegateCreated(delegate)

    picker.delegate = delegate

    viewController.presentViewController(
        viewControllerToPresent = picker,
        animated = true,
        completion = null
    )
}

private suspend fun compareImages(
    capturedImage: UIImage,
    targetImageUrl: String
): Pair<Boolean, String> {

    val targetImage = downloadImage(targetImageUrl)
        ?: return false to "Failed to load target image"

    val capturedLabels = extractMeaningfulLabels(classifyImage(capturedImage))
    val targetLabels = extractMeaningfulLabels(classifyImage(targetImage))

    val matchedLabels = capturedLabels.intersect(targetLabels.toSet())

    return if (matchedLabels.isNotEmpty()) {
        true to "Match found: ${matchedLabels.first()}"
    } else {
        val capturedDescription =
            capturedLabels.ifEmpty { listOf("unknown object") }.joinToString()

        val targetDescription =
            targetLabels.ifEmpty { listOf("target object") }.joinToString()

        false to "Mismatch: Captured ($capturedDescription) vs Expected ($targetDescription)"
    }
}

private fun extractMeaningfulLabels(
    observations: List<VNClassificationObservation>
): List<String> {
    return observations
        .asSequence()
        .filter { it.confidence >= 0.7f }
        .take(5)
        .flatMap { observation ->
            observation.identifier
                .lowercase()
                .split(",")
                .map(String::trim)
        }
        .filter(::isMeaningfulLabel)
        .distinct()
        .take(3)
        .toList()
}

private fun isMeaningfulLabel(label: String): Boolean {
    return label.length > 2 &&
            GenericLabels.none { generic ->
                label.contains(generic)
            }
}

@OptIn(ExperimentalForeignApi::class)
private suspend fun classifyImage(
    image: UIImage
): List<VNClassificationObservation> = withContext(Dispatchers.Default) {

    val cgImage = image.CGImage ?: return@withContext emptyList()

    val request = VNClassifyImageRequest()

    val handler = VNImageRequestHandler(
        cGImage = cgImage,
        options = emptyMap<Any?, Any>()
    )

    val success = handler.performRequests(
        requests = listOf(request),
        error = null
    )

    if (!success) {
        return@withContext emptyList()
    }

    @Suppress("UNCHECKED_CAST")
    (request.results as? List<VNClassificationObservation>).orEmpty()
}

private suspend fun downloadImage(
    url: String
): UIImage? = withContext(Dispatchers.Default) {

    runCatching {
        val nsUrl = NSURL.URLWithString(url) ?: return@withContext null
        val data = NSData.dataWithContentsOfURL(nsUrl) ?: return@withContext null

        UIImage.imageWithData(data)
    }.getOrNull()
}