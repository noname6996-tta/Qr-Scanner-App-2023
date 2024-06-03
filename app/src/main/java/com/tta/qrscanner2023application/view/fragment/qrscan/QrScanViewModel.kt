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
    val errorMessage = MutableLiveData<String>()
    val qrCodeEntity = MutableLiveData<QrCodeEntity>()
    fun getListQrByType(typeCode: TypeCode) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getAllQrList(typeCode)
                }
            }
                .onSuccess {
                    if (typeCode == TypeCode.CREATED) {
                        listQrCodeCreate.value = it
                    } else {
                        listQrCodeScan.value = it
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

    fun deleteQrCode(typeCode: TypeCode, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.deleteQrById(id)
            }.onSuccess {
                getListQrByType(typeCode) // Reload the list after successful deletion
            }.onFailure {
                errorMessage.value = it.message.toString()
            }
        }
    }

    fun getInfoById(id: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getInfoById(id)
                }
            }
                .onSuccess {
                    qrCodeEntity.value = it
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }
}