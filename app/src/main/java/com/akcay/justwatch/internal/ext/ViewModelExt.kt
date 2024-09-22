package com.akcay.justwatch.internal.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.firebase.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launchCatching(
    logService: LogService,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
        context = CoroutineExceptionHandler { _, throwable ->
            logService.logNonFatalCrash(throwable)
        },
        block = block
    )
}