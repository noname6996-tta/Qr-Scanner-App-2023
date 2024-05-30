package com.tta.qrscanner2023application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tta.qrscanner2023application.data.util.Constants
import javax.annotation.Nonnull

@Entity(tableName = Constants.TABLE_NAME)
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_ID) val id: Int,
    @ColumnInfo(name = Constants.COLUMN_CODE) val code: String,
    @ColumnInfo(name = Constants.COLUMN_CREATED_TIME) val createdTime: String,
    @ColumnInfo(name = Constants.COLUMN_TYPE) val type: TypeCode
)
