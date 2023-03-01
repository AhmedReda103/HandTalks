package com.example.handtalks.other

import android.graphics.*
import androidx.camera.core.ImageProxy
import com.example.handtalks.other.Constants.ARSL_PATH
import com.example.handtalks.other.Constants.ASL
import com.example.handtalks.other.Constants.ASL_PATH
import java.io.ByteArrayOutputStream

var selectedModel = ASL
var modelPath = ASL_PATH
val languageCodes = arrayOf("ARSL", "ASL")
val languageModels = mapOf(languageCodes.first() to ARSL_PATH, languageCodes.last() to ASL_PATH)

fun ImageProxy.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

val aslChars = arrayOf(
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
    "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"
)

val ArslChar = arrayOf(
    "ain", "aleff", "baa", "daad", "dal", "fa", "Qaaf", "ghain",
    "ha", "haa", "jem", "kaaf", "khaa", "laam", "meem", "nun", "ra", "saad", "seen", "sheen",
    "ta", "taa", "tha", "thah", "thal", "waw", "ya", "zay"
)

fun cropBitmap(bitmap: Bitmap, rect: Rect): Bitmap =
    Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height())

