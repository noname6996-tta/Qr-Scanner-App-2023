package com.tta.qrscanner2023application.view.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.qrscanner2023application.data.util.DataStoreKeys
import com.tta.qrscanner2023application.data.util.SettingsDataStore
import com.tta.qrscanner2023application.data.util.qrData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingViewModel(private val context: Context) : ViewModel() {
    val soundData = MutableLiveData<Boolean>()
    val vibrationData = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    fun getData() {
        viewModelScope.launch {
            context.qrData.data.collect {
                soundData.value = it[DataStoreKeys.SOUND] ?: true
                vibrationData.value = it[DataStoreKeys.VIBRATION] ?: true
            }
        }
    }

    fun changeVibration(value: Boolean) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    SettingsDataStore(context.qrData).saveVibrateToPreferencesStore(value, context)
                }
            }
                .onSuccess {
                    message.value = "Change Vibration Success"
                }
                .onFailure {
                    message.value = "Change Vibration Fail"
                }
        }
    }

    fun changeSound(value: Boolean) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    SettingsDataStore(context.qrData).saveSoundToPreferencesStore(value, context)
                }
            }
                .onSuccess {
                    message.value = "Change Sound Success"
                }
                .onFailure {
                    message.value = "Change Sound Fail"
                }
        }
    }
}