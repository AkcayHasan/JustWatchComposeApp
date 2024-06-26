package com.akcay.justwatch.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.repository.MovieRepository
import com.akcay.justwatch.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    val storeManager = dataStoreManager
}