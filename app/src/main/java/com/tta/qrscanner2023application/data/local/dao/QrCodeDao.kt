package com.tta.qrscanner2023application.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tta.qrscanner2023application.data.local.base.BaseDao
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.util.Constants

@Dao
interface QrCodeDao : BaseDao<QrCodeEntity> {
    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE type = :typeCode")
    suspend fun getQrCodeListSCAN(typeCode: TypeCode) : List<QrCodeEntity>

    @Query("DELETE FROM ${Constants.TABLE_NAME} WHERE id = :id")
    suspend fun deleteQrCodeWithID(id: Int)

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE id = :id")
    suspend fun getInfoByID(id: Int) : QrCodeEntity

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun deleteAllQrCodes()
}