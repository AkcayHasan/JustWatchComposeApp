package com.akcay.justwatch

import androidx.lifecycle.ViewModel
import com.akcay.justwatch.internal.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    dataStoreManager: DataStoreManager
) : ViewModel() {

    val storeManager = dataStoreManager
}