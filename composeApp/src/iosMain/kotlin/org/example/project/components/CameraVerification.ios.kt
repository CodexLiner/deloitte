package org.example.project.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.uikit.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.UIKit.*
import platform.Vision.*
import platform.Foundation.*
import platform.CoreGraphics.*
import platform.CoreImage.*
import platform.darwin.NSObject

@Composable
actual fun CameraVerificationButton(
    itemName: String,
    imageUrl: String,
    modifier: Modifier,
    onResult: (Boolean, String) -> Unit,
) {
    val viewController = LocalUIViewController.current
    val scope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }


    var currentDelegate by remember { mutableStateOf<NSObject?>(null) }

    Button(
        onClick = {
            if (!UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
                onResult(false, "Camera not available")
                return@Button
            }

            val picker = UIImagePickerController()
            picker.sourceType =
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera

            val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val image =
                        didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                    picker.dismissViewControllerAnimated(true, null)
                    currentDelegate = null

                    if (image != null) {
                        isProcessing = true
                        scope.launch {
                            try {
                                val result = performSemanticComparison(image, imageUrl)
                                onResult(result.first, result.second)
                            } catch (e: Exception) {
                                onResult(false, "Comparison failed: ${e.message}")
                            } finally {
                                isProcessing = false
                            }
                        }
                    } else {
                        onResult(false, "No image captured")
                    }
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, null)
                    currentDelegate = null
                }
            }

            currentDelegate = delegate
            picker.delegate = delegate
            viewController.presentViewController(picker, animated = true, completion = null)
        },
        modifier = modifier,
        enabled = !isProcessing,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFDA291C),
            disabledContainerColor = Color.Gray
        )
    ) {
        Text(
            text = if (isProcessing) "Analyzing..." else "Verify with Camera",
            color = Color.White
        )
    }
}

private suspend fun performSemanticComparison(
    capturedImage: UIImage,
    targetUrl: String
): Pair<Boolean, String> {

    val targetImage = downloadImage(targetUrl) ?: return Pair(false, "Failed to load target image")

    val capturedObservations = classifyImage(capturedImage)
    val targetObservations = classifyImage(targetImage)

    // Expanded list of generic labels to ignore
    val genericLabels = setOf(
        "food", "dish", "cuisine", "meal", "fast food", "produce",
        "ingredient", "snack", "junk food", "prepared food", "recipe",
        "tableware", "plate", "bread", "bun", "baked goods", "processed food",
        "nutrition", "appetizer", "side dish", "delicacy", "meat"
    )

    fun getMeaningfulLabels(observations: List<VNClassificationObservation>): List<String> {
        return observations.asSequence()
            .filter { it.confidence >= 0.7f } // Increased threshold for higher precision
            .take(5) // Only look at top classifications
            .flatMap { obs ->
                obs.identifier.lowercase().split(",").map { it.trim() }
            }
            .filter { label ->
                label.length > 2 && genericLabels.none { generic -> label.contains(generic) }
            }
            .distinct()
            .take(3) // Focus on the most specific meaningful labels
            .toList()
    }

    val capturedLabels = getMeaningfulLabels(capturedObservations)
    val targetLabels = getMeaningfulLabels(targetObservations)

    // Match if there is any intersection in the top specific labels
    val commonLabels = capturedLabels.intersect(targetLabels.toSet())

    return if (commonLabels.isNotEmpty()) {
        Pair(true, "Match found: ${commonLabels.first()}")
    } else {
        val capturedText = if (capturedLabels.isNotEmpty()) capturedLabels.joinToString(", ") else "unknown object"
        val targetText = if (targetLabels.isNotEmpty()) targetLabels.joinToString(", ") else "target object"
        
        Pair(false, "Mismatch: Captured ($capturedText) vs Expected ($targetText)")
    }
}

@OptIn(ExperimentalForeignApi::class)
private suspend fun classifyImage(image: UIImage): List<VNClassificationObservation> =
    withContext(Dispatchers.Default) {
        val cgImage = image.CGImage ?: return@withContext emptyList()
        val handler = VNImageRequestHandler(cGImage = cgImage, options = emptyMap<Any?, Any>())
        val request = VNClassifyImageRequest()

        val success = handler.performRequests(listOf(request), null)
        if (success) {
            @Suppress("UNCHECKED_CAST")
            (request.results as? List<VNClassificationObservation>) ?: emptyList()
        } else {
            emptyList()
        }
    }

private suspend fun downloadImage(url: String): UIImage? = withContext(Dispatchers.Default) {
    try {
        val nsUrl = NSURL.URLWithString(url) ?: return@withContext null
        val data = NSData.dataWithContentsOfURL(nsUrl) ?: return@withContext null
        UIImage.imageWithData(data)
    } catch (e: Exception) {
        null
    }
}
