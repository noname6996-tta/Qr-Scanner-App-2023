package com.tta.qrscanner2023application.view.fragment.qrscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val repository: QrCodeRepository
) : ViewModel() {
    fun getListQrByType(typeCode: TypeCode){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllQrList(typeCode)
        }
    }

    fun insertQrCode(qrCodeEntity: QrCodeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQr(qrCodeEntity)
        }
    }

    fun deleteQrCode(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQrById(id)
        }
    }
}