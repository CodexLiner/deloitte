package org.example.project.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import platform.UIKit.*
import platform.Foundation.*
import platform.Vision.*
import platform.CoreGraphics.*
import platform.CoreImage.*
import platform.objc.*
import platform.darwin.*
import kotlinx.cinterop.*

private class CameraDelegate(
    var itemName: String,
    var imageUrl: String,
    var onResult: (Boolean, String) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
    
    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        val capturedImage = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        if (capturedImage != null) {
            compareImages(capturedImage, imageUrl, itemName, onResult)
        }
        picker.dismissViewControllerAnimated(true, null)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, null)
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraVerificationButton(
    itemName: String,
    imageUrl: String,
    modifier: Modifier,
    onResult: (Boolean, String) -> Unit
) {
    val delegate = remember { CameraDelegate(itemName, imageUrl, onResult) }
    delegate.itemName = itemName
    delegate.imageUrl = imageUrl
    delegate.onResult = onResult

    Button(
        onClick = {
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            val picker = UIImagePickerController()
            if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
                picker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                picker.delegate = delegate
                rootViewController?.presentViewController(picker, animated = true, completion = null)
            } else {
                onResult(false, "Camera not available on this device")
            }
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27251F))
    ) {
        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White)
        Text("Scan", modifier = Modifier.padding(start = 8.dp), color = Color.White)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun compareImages(capturedImage: UIImage, referenceUrl: String, itemName: String, onResult: (Boolean, String) -> Unit) {
    val url = NSURL.URLWithString(referenceUrl) ?: run {
        onResult(false, "Invalid reference image URL")
        return
    }
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
        val data = NSData.dataWithContentsOfURL(url)
        val referenceImage = data?.let { UIImage.imageWithData(it) }
        
        if (referenceImage == null) {
            dispatch_async(dispatch_get_main_queue()) {
                onResult(false, "Could not load reference image")
            }
            return@dispatch_async
        }

        val capturedCg = capturedImage.CGImage
        val referenceCg = referenceImage.CGImage
        
        if (capturedCg == null || referenceCg == null) {
            dispatch_async(dispatch_get_main_queue()) {
                onResult(false, "Error processing images")
            }
            return@dispatch_async
        }

        val handler1 = VNImageRequestHandler(capturedCg, emptyMap<Any?, Any?>())
        val handler2 = VNImageRequestHandler(referenceCg, emptyMap<Any?, Any?>())
        
        val request1 = VNGenerateImageFeaturePrintRequest()
        val request2 = VNGenerateImageFeaturePrintRequest()
        
        try {
            handler1.performRequests(listOf(request1), null)
            handler2.performRequests(listOf(request2), null)
            
            val fp1 = request1.results?.firstOrNull() as? VNFeaturePrintObservation
            val fp2 = request2.results?.firstOrNull() as? VNFeaturePrintObservation
            
            if (fp1 != null && fp2 != null) {
                val distance = memScoped {
                    val distancePtr = alloc<FloatVar>()
                    val error = alloc<ObjCObjectVar<NSError?>>()
                    fp1.computeDistance(distancePtr.ptr, fp2, error.ptr)
                    distancePtr.value
                }
                
                dispatch_async(dispatch_get_main_queue()) {
                    if (distance < 20.0f) {
                        onResult(true, "Verified! It's a match.")
                    } else {
                        onResult(false, "That doesn't look like the right $itemName.")
                    }
                }
            } else {
                dispatch_async(dispatch_get_main_queue()) {
                    onResult(false, "Similarity check failed")
                }
            }
        } catch (e: Exception) {
            dispatch_async(dispatch_get_main_queue()) {
                onResult(false, "Comparison error: ${e.message}")
            }
        }
    }
}
