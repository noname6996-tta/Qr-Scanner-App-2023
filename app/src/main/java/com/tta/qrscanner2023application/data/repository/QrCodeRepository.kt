package com.tta.qrscanner2023application.data.repository

import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode

interface QrCodeRepository {
    suspend fun getAllQrList(typeCode: TypeCode): List<QrCodeEntity>
    suspend fun insertQr(qrCodeEntity: QrCodeEntity): Unit
    suspend fun deleteQrById(id: Int): Unit

    suspend fun getInfoById(id: Int): QrCodeEntity
}