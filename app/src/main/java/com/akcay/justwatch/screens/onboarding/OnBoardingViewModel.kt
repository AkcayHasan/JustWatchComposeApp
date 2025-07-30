package com.akcay.justwatch.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.internal.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun saveOnBoardingVisibility() {
        viewModelScope.launch {
            dataStoreManager.saveOnBoardingVisibility(isShown = false)
        }
    }
}
