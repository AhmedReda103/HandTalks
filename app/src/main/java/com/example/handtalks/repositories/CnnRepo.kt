package com.example.handtalks.repositories

import android.graphics.Bitmap

interface CnnRepo {
    suspend fun detectionAndClassification(bitmap: Bitmap, classes: Array<String>)
    fun getLabel() : String?
}