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
    val listQrCodeScan = MutableLiveData<List<QrCodeEntity>>()
    val listQrCodeCreate = MutableLiveData<List<QrCodeEntity>>()
    val deleteScanQrCode = MutableLiveData<Boolean>()
    val deleteCreateQrCode = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    fun getListQrByType(typeCode: TypeCode) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getAllQrList(typeCode)
                }
            }
                .onSuccess {
                    if (typeCode == TypeCode.CREATED) {
                        listQrCodeCreate.value = repository.getAllQrList(typeCode)
                    } else {
                        listQrCodeScan.value = repository.getAllQrList(typeCode)
                    }
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

    fun deleteQrCode(typeCode: TypeCode,id: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.deleteQrById(id)
                }
            }
                .onSuccess {
                    if (typeCode == TypeCode.SCAN){
                        deleteScanQrCode.value = true
                    } else
                    deleteCreateQrCode.value = true
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }
}