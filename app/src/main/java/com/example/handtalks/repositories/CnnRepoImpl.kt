package com.example.handtalks.repositories

import android.graphics.Bitmap
import com.example.handtalks.cnn.ModelsInterpreterImpl
import javax.inject.Inject

class CnnRepoImpl @Inject constructor(
    private val modelsInterpreterImpl: ModelsInterpreterImpl
) : CnnRepo {

    override suspend fun detectionAndClassification(bitmap: Bitmap, classes: Array<String>) {
        modelsInterpreterImpl.detectionThenClassification(bitmap, classes)
    }

    override fun getLabel(): String? = modelsInterpreterImpl.getLabel()

}