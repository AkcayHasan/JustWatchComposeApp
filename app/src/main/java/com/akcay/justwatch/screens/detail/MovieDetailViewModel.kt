package com.akcay.justwatch.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movieId: String = savedStateHandle.get<String>(MOVIE_ID_SAVED_STATE_KEY)!!

    companion object {
        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
    }
}