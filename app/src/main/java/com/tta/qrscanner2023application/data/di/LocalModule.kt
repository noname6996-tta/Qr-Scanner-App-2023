package com.tta.qrscanner2023application.data.di

import android.content.Context
import com.tta.qrscanner2023application.data.local.dao.QrCodeDao
import com.tta.qrscanner2023application.data.local.db.QrCodeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideQrCodeDatabase(
        @ApplicationContext context: Context
    ): QrCodeDatabase {
        return QrCodeDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideQrCodeDao(appDatabase: QrCodeDatabase): QrCodeDao {
        return appDatabase.qrCodeDao()
    }
}