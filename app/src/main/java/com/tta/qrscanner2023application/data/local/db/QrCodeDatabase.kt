package com.tta.qrscanner2023application.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tta.qrscanner2023application.data.local.dao.QrCodeDao
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.util.Constants

@Database(
    entities = [QrCodeEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(LocalDateConverter::class)
abstract class QrCodeDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QrCodeDao

    companion object {
        @Volatile
        private var instance: QrCodeDatabase? = null

        fun getDatabase(context: Context): QrCodeDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, QrCodeDatabase::class.java, Constants.TABLE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}