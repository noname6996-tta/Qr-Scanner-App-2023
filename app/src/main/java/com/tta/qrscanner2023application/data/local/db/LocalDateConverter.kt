package com.tta.qrscanner2023application.data.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@ProvidedTypeConverter
class LocalDateConverter {
    @TypeConverter
    fun fromDate(value: Date?): Long? {
        return value?.time // Lấy giá trị timestamp (số mili giây)
    }

    // Chuyển từ Long (timestamp) về Date
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) } // Chuyển lại thành Date từ timestamp
    }
}