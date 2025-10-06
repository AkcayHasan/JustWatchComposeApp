package com.akcay.justwatch.data.repository

import com.akcay.justwatch.domain.repository.LogRepository
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
class LogRepositoryImpl(): LogRepository {

    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}