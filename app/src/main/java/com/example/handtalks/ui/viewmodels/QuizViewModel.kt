package com.example.handtalks.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private var _charModelListenerQuiz = MutableLiveData<String>()
    var charModelListenerQuiz : LiveData<String> = _charModelListenerQuiz

    fun setLiveData(label: String?) {
        _charModelListenerQuiz.postValue(label!!)
    }


}