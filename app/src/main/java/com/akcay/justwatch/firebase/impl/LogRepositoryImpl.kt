package com.akcay.justwatch.firebase.impl

import com.akcay.justwatch.firebase.service.LogRepository
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor(): LogRepository {

    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}