package com.example.handtalks.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreManager @Inject constructor(context: Context) {

    private val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(name = "LANGUAGE_KEY")
    private val languageDataStore = context.languageDataStore
    private val Context.isFirstTimeLaunchDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "IS_FIRST_TIME_LAUNCH_KEY"
    )
    private val isFirstTimeLaunchDataStore = context.isFirstTimeLaunchDataStore

    companion object {
        val isFirstTimeLaunchKey = booleanPreferencesKey("IS_FIRST_TIME_LAUNCH_KEY")
        val signLanguageKey = stringPreferencesKey("SIGN_LANGUAGE_KEY")
    }

    suspend fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean) {
        isFirstTimeLaunchDataStore.edit { preferences ->
            preferences[isFirstTimeLaunchKey] = isFirstTimeLaunch
        }
    }

    fun isFirstTimeLaunch(): Flow<Boolean> {
        return isFirstTimeLaunchDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val isFirstTimeLaunch = preferences[isFirstTimeLaunchKey] ?: false
                isFirstTimeLaunch
            }
    }




    /************************/

    suspend fun setLanguage(language: String) {
        languageDataStore.edit { preferences ->
            preferences[signLanguageKey] = language
        }
    }

    fun getLanguage(): Flow<String> {
        return languageDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val lang = preferences[signLanguageKey]
                lang.toString()
            }
    }
}
