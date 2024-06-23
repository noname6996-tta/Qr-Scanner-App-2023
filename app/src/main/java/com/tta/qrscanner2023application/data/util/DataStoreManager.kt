package com.tta.qrscanner2023application.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tta.qrscanner2023application.data.util.DataStoreKeys.LANGUAGE
import com.tta.qrscanner2023application.data.util.DataStoreKeys.SOUND
import com.tta.qrscanner2023application.data.util.DataStoreKeys.VIBRATION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.qrData: DataStore<Preferences> by preferencesDataStore(name = "qrData")


object DataStoreKeys {
    val VIBRATION = booleanPreferencesKey("vibration")
    val SOUND = booleanPreferencesKey("sound")
    val LANGUAGE = stringPreferencesKey("language")
}

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {
    val preferenceFlow: Flow<Any> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            preferences[VIBRATION] ?: true
            preferences[SOUND] ?: true
            preferences[LANGUAGE] ?: "Locale.ENGLISH.language"
        }

    suspend fun saveVibrateToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.qrData.edit { preferences ->
            preferences[VIBRATION] = isLinearLayoutManager
        }
    }

    suspend fun saveSoundToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.qrData.edit { preferences ->
            preferences[SOUND] = isLinearLayoutManager
        }
    }

    suspend fun saveLanguageToPreferencesStore(isLinearLayoutManager: String, context: Context) {
        context.qrData.edit { preferences ->
            preferences[LANGUAGE] = isLinearLayoutManager
        }
    }
}