package com.example.handtalks.ui.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handtalks.repositories.CnnRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cnnRepoImpl: CnnRepoImpl
): ViewModel() {

    fun detectionAndClassification(bitmap: Bitmap, classes: Array<String>) {
        viewModelScope.launch {
            cnnRepoImpl.detectionAndClassification(bitmap, classes)
        }
    }

    fun getLabel(): String? = cnnRepoImpl.getLabel()


}
