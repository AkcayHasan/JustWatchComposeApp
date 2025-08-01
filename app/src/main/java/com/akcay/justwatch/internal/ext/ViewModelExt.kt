package com.akcay.justwatch.internal.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.LogRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launchCatching(
  logRepository: LogRepository,
  block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
        context = CoroutineExceptionHandler { _, throwable ->
            logRepository.logNonFatalCrash(throwable)
        },
        block = block
    )
}
