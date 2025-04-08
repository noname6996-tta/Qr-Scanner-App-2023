package com.tta.qrscanner2023application.data.repository

import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode

interface QrCodeRepository {
    suspend fun getAllQrList(typeCode: TypeCode): List<QrCodeEntity>
    suspend fun insertQr(qrCodeEntity: QrCodeEntity)
    suspend fun deleteQrById(id: Int)

    suspend fun getInfoById(id: Int): QrCodeEntity
    suspend fun deleteAll()
}