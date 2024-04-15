package com.akcay.justwatch.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")

        private val onBoardingVisibility = booleanPreferencesKey("onBoardingVisibility")
    }

    suspend fun shouldOnBoardingVisible() = context.dataStore.data.map { preferences ->
        preferences[onBoardingVisibility] ?: false
    }.first()

    suspend fun saveOnBoardingVisibility(isShown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onBoardingVisibility] = isShown
        }
    }
}