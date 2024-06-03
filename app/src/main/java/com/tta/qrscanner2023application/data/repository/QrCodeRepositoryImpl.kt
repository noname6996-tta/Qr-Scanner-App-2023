package com.tta.qrscanner2023application.data.repository

import com.tta.qrscanner2023application.data.local.dao.QrCodeDao
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import javax.inject.Inject

class QrCodeRepositoryImpl @Inject constructor(
    private val dao: QrCodeDao
) : QrCodeRepository {
    override suspend fun getAllQrList(typeCode: TypeCode): List<QrCodeEntity> {
        return dao.getQrCodeListSCAN(typeCode)
    }

    override suspend fun insertQr(qrCodeEntity: QrCodeEntity) = dao.insert(qrCodeEntity)

    override suspend fun deleteQrById(id: Int) = dao.deleteQrCodeWithID(id)
    override suspend fun getInfoById(id: Int) = dao.getInfoByID(id)
}