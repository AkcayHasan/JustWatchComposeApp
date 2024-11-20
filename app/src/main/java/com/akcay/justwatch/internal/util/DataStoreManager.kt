package com.akcay.justwatch.internal.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
  @ApplicationContext private val context: Context
) {

  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")

    private val onBoardingVisibility = booleanPreferencesKey("onBoardingVisibility")
    private val rememberedEmailKey = stringPreferencesKey("rememberedEmail")
    private val rememberedPasswordKey = stringPreferencesKey("rememberedPassword")
    private val rememberMeCheckboxKey = booleanPreferencesKey("rememberMeCheckbox")
  }

  suspend fun shouldOnBoardingVisible() = context.dataStore.data.map { preferences ->
    preferences[onBoardingVisibility] ?: true
  }.first()

  suspend fun saveOnBoardingVisibility(isShown: Boolean) {
    context.dataStore.edit { preferences ->
      preferences[onBoardingVisibility] = isShown
    }
  }

  suspend fun saveRememberedEmail(email: String) {
    context.dataStore.edit { preferences ->
      preferences[rememberedEmailKey] = email
    }
  }

  suspend fun getRememberedEmail(): String? {
    return context.dataStore.data.map { preferences ->
      preferences[rememberedEmailKey]
    }.firstOrNull()
  }

  suspend fun clearRememberedEmail() {
    context.dataStore.edit { preferences ->
      preferences.remove(rememberedEmailKey)
    }
  }

  suspend fun saveRememberMeCheckboxStatus(isChecked: Boolean) {
    context.dataStore.edit { preferences ->
      preferences[rememberMeCheckboxKey] = isChecked
    }
  }

  suspend fun getRememberMeCheckboxStatus() = context.dataStore.data.map { preferences ->
    preferences[rememberMeCheckboxKey] ?: false
  }.first()

  suspend fun saveRememberedPassword(password: String) {
    context.dataStore.edit { preferences ->
      preferences[rememberedPasswordKey] = password
    }
  }

  suspend fun getRememberedPassword(): String? {
    return context.dataStore.data.map { preferences ->
      preferences[rememberedPasswordKey]
    }.firstOrNull()
  }

  suspend fun clearRememberedPassword() {
    context.dataStore.edit { preferences ->
      preferences.remove(rememberedPasswordKey)
    }
  }
}