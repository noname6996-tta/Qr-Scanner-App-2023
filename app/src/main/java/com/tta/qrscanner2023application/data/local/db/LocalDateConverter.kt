package com.tta.qrscanner2023application.data.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
class LocalDateConverter {
    @TypeConverter
    fun fromText(value: String) : LocalDate {
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toText(date: LocalDate) : String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}