package com.example.handtalks.cnn

import android.graphics.Bitmap
import android.graphics.Rect
import com.example.handtalks.other.cropBitmap
import org.tensorflow.lite.Interpreter
import java.lang.reflect.Array
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class ModelsInterpreterImpl @Inject constructor(
    @Named("classifier") private val interpreterClassifier: Interpreter,
    @Named("detection") private val interpreterDetection: Interpreter,
) : ModelsInterpreter {
    companion object {
        private const val CHANNEL_SIZE = 3
        private var inputImageWidth: Int = 0
        private var inputImageHeight: Int = 0
        private var modelInputSize: Int = 0
        private var returnLabel: String? = ""
    }

    init {
        val inputShape = interpreterClassifier.getInputTensor(0).shape()
        inputImageWidth = inputShape[1]
        inputImageHeight = inputShape[2]
        modelInputSize = Float.SIZE_BYTES * inputImageWidth * inputImageHeight * CHANNEL_SIZE
    }

    override suspend fun convertBitmapToByteBufferForClassification(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(modelInputSize)
        byteBuffer.order(ByteOrder.nativeOrder())
        val pixels = IntArray(inputImageWidth * inputImageHeight)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until inputImageWidth) {
            for (j in 0 until inputImageHeight) {
                val pixelVal = pixels[pixel++]
                byteBuffer.putFloat(((pixelVal shr 16 and 0xFF).toFloat()))
                byteBuffer.putFloat((pixelVal shr 8 and 0xFF).toFloat())
                byteBuffer.putFloat((pixelVal and 0xFF).toFloat())
            }
        }
        bitmap.recycle()
        return byteBuffer
    }

    override suspend fun convertBitmapToByteBufferForDetection(bitmap: Bitmap): ByteBuffer {
        val sizeImages = 300
        val byteBuffer = ByteBuffer.allocateDirect(4 * 1 * sizeImages * sizeImages * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(sizeImages * sizeImages)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until sizeImages) {
            for (j in 0 until sizeImages) {
                val pixelVal = intValues[pixel++]
                byteBuffer.putFloat(((pixelVal shr 16 and 0xFF) - 127.5f) / 127.5f)
                byteBuffer.putFloat(((pixelVal shr 8 and 0xFF) - 127.5f) / 127.5f)
                byteBuffer.putFloat(((pixelVal and 0xFF) - 127.5f) / 127.5f)
            }
        }
        return byteBuffer
    }

    override suspend fun classify(bitmap: Bitmap, classes: kotlin.Array<String>): String {
        val resizedImage =
            Bitmap.createScaledBitmap(bitmap, inputImageWidth, inputImageHeight, true)
        resizedImage.config = Bitmap.Config.ARGB_8888
        val byteBuffer = convertBitmapToByteBufferForClassification(resizedImage)
        val output = Array(1) { FloatArray(classes.size) }
        interpreterClassifier.run(byteBuffer, output)
        val index = output[0].indices.maxBy { output[0][it] }
        return classes[index]
    }

    override suspend fun detectionThenClassification(
        bitmap: Bitmap,
        classes: kotlin.Array<String>
    ) {
        val heightFrame = bitmap.height
        val widthFrame = bitmap.width
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, 300, 300, true)
        val byteBuffer = convertBitmapToByteBufferForDetection(scaledBitmap)
        val input = arrayOf<Any>(1)
        input[0] = byteBuffer
        val outputMap: MutableMap<Int, Any> = TreeMap()
        val boxes = Array(1) {
            Array(10) {
                FloatArray(4)
            }
        }
        val scores = Array(1) {
            FloatArray(
                10
            )
        }
        val classess = Array(1) {
            FloatArray(
                10
            )
        }
        outputMap[0] = boxes
        outputMap[1] = classess
        outputMap[2] = scores
        interpreterDetection.runForMultipleInputsOutputs(input, outputMap)
        val value = outputMap[0]!!
        val score = outputMap[2]!!
        for (i in 0..9) {
            val scoreValue = Array.get(score, 0)
                ?.let { scoree -> Array.get(scoree, i) } as Float
            if (scoreValue > 0.5) {
                val box1: Any? = Array.get(value, 0)
                    ?.let { boxy -> Array.get(boxy, i) }
                var y1 = box1?.let { yfirst ->
                    Array.get(
                        yfirst,
                        0
                    )
                } as Float * heightFrame
                var x1 = Array.get(box1, 1) as Float * widthFrame
                var y2 = Array.get(box1, 2) as Float * heightFrame
                var x2 = Array.get(box1, 3) as Float * widthFrame
                y1 = y1.coerceAtLeast(0F)
                x1 = x1.coerceAtLeast(0F)
                x2 = x2.coerceAtMost(widthFrame.toFloat())
                y2 = y2.coerceAtMost(heightFrame.toFloat())
                val croppedHandRect = Rect(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
                val croppedBitmap = cropBitmap(bitmap, croppedHandRect)
                returnLabel = classify(croppedBitmap, classes)
            }
        }
    }

    override fun getLabel(): String? = returnLabel
}