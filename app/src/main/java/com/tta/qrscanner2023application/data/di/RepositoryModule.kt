package com.tta.qrscanner2023application.data.di

import com.tta.qrscanner2023application.data.local.dao.QrCodeDao
import com.tta.qrscanner2023application.data.repository.QrCodeRepository
import com.tta.qrscanner2023application.data.repository.QrCodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQrCodeRepository(
        dao : QrCodeDao
    ) : QrCodeRepository {
        return QrCodeRepositoryImpl(dao)
    }
}