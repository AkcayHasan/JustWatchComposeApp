package com.akcay.justwatch.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.internal.util.DataStoreManager
import kotlinx.coroutines.launch
class OnBoardingViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun saveOnBoardingVisibility() {
        viewModelScope.launch {
            dataStoreManager.saveOnBoardingVisibility(isShown = false)
        }
    }
}
