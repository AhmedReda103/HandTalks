package com.example.handtalks.cnn

import android.graphics.Bitmap
import java.nio.ByteBuffer

interface ModelsInterpreter {
    suspend fun convertBitmapToByteBufferForClassification(bitmap: Bitmap): ByteBuffer
    suspend fun convertBitmapToByteBufferForDetection(bitmap: Bitmap): ByteBuffer
    suspend fun classify(bitmap: Bitmap, classes: Array<String>): String
    suspend fun detectionThenClassification(bitmap: Bitmap, classes: Array<String>)
    fun getLabel() : String?
}