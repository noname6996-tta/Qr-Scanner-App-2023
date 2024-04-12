package com.tta.qrscanner2023application.data.di

import android.content.Context
import com.tta.qrscanner2023application.QrScannerApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): QrScannerApp {
        return app as QrScannerApp
    }
}