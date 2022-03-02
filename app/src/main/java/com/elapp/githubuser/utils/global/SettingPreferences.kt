package com.elapp.githubuser.utils.global

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.elapp.githubuser.utils.global.ConstVal.THEME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(THEME_KEY.toString())

@Singleton
class SettingPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val settingDataStore = context.dataStore

    fun getThemeSetting(): Flow<Boolean> = settingDataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

}