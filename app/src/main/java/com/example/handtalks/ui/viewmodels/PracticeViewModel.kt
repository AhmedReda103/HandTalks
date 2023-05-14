package com.example.handtalks.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handtalks.other.modelPath
import com.example.handtalks.other.selectedModel
import com.example.handtalks.repositories.CnnRepoImpl
import com.example.handtalks.repositories.PracticeItemsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class PracticeViewModel : ViewModel() {

    private var _charModelListenerPractice = MutableLiveData<String>()
    var charModelListenerPractice : LiveData<String> = _charModelListenerPractice

    private val repoPractice = PracticeItemsRepo




    fun getImagesForFirstItem():ArrayList<Int>{
        return repoPractice.getFirstItemList(modelPath).get(0).images
    }

    fun getImagesForSecondItem():ArrayList<Int>{
        return repoPractice.getSecondItemList(modelPath).get(0).images
    }

    fun getImagesForThirdItem():ArrayList<Int>{
        return repoPractice.getThirdItemList(modelPath).get(0).images
    }

    fun getCharForFirstItem():ArrayList<String>{
        return repoPractice.getFirstItemList(modelPath).get(0).characters
    }

    fun getCharForSecondItem():ArrayList<String>{
        return repoPractice.getSecondItemList(modelPath).get(0).characters
    }

    fun getCharForThirdItem():ArrayList<String>{
        return repoPractice.getThirdItemList(modelPath).get(0).characters
    }

    fun setLiveData(label: String?) {
        _charModelListenerPractice.postValue(label!!)
    }


}