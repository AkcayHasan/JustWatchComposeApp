package com.akcay.justwatch.firebase.service

interface LogRepository {

    fun logNonFatalCrash(throwable: Throwable)
}