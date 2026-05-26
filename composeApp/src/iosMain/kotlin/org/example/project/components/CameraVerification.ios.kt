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


    val genericLabels = setOf(
        "food", "dish", "cuisine", "meal", "fast food", "produce",
        "ingredient", "snack", "junk food", "prepared food", "recipe"
    )


    fun getMeaningfulLabels(observations: List<VNClassificationObservation>): Set<String> {
        return observations.asSequence()
            .filter { it.confidence >= 0.3f }
            .flatMap { obs ->

                obs.identifier.lowercase().split(",").map { it.trim() }
            }
            .filter { label ->

                label.length > 2 && genericLabels.none { generic -> label.contains(generic) }
            }
            .toSet()
    }

    val capturedLabels = getMeaningfulLabels(capturedObservations)
    val targetLabels = getMeaningfulLabels(targetObservations)


    val commonLabels = capturedLabels.intersect(targetLabels)

    return if (commonLabels.isNotEmpty()) {

        Pair(true, "Match found: ${commonLabels.first()}")
    } else {
        val detectedText = if (capturedLabels.isNotEmpty()) {
            "Detected: ${capturedLabels.take(2).joinToString(", ")}"
        } else {
            "No specific object detected"
        }
        Pair(false, "Mismatch. $detectedText")
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
