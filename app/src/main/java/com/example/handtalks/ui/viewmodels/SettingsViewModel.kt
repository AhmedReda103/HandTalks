package com.example.handtalks.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.handtalks.data.local.DataStoreManager
import com.example.handtalks.qualifiers.IOThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor
    (
    @IOThread
    private val dispatcher: CoroutineDispatcher,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    // private val dataStore = DataStoreManager(context)

    init{

    }

    val isFirstTimeLaunch = dataStoreManager.isFirstTimeLaunch().asLiveData(dispatcher)
    @OptIn(DelicateCoroutinesApi::class)
    fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean) {
        GlobalScope.launch(dispatcher) {
            dataStoreManager.setFirstTimeLaunch(isFirstTimeLaunch)
        }
    }


    val getLanguage = dataStoreManager.getLanguage().asLiveData(dispatcher)

    @OptIn(DelicateCoroutinesApi::class)
    fun setLanguage(language: String) {
        GlobalScope.launch(dispatcher) {
            dataStoreManager.setLanguage(language)
        }
    }



}