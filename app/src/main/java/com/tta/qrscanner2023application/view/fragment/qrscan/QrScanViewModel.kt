package com.tta.qrscanner2023application.view.fragment.qrscan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val repository: QrCodeRepository
) : ViewModel() {
    val listQrCode = MutableLiveData<List<QrCodeEntity>>()
    val errorMessage = MutableLiveData<String>()
    fun getListQrByType(typeCode: TypeCode) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getAllQrList(typeCode)
                }
            }
                .onSuccess {
                    listQrCode.value = repository.getAllQrList(typeCode)
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }

    fun insertQrCode(qrCodeEntity: QrCodeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQr(qrCodeEntity)
        }
    }

    fun deleteQrCode(id: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.deleteQrById(id)
                }
            }
                .onSuccess {
                    errorMessage.value = "Success"
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }
}