package com.akcay.justwatch.firebase.service

interface LogService {

    fun logNonFatalCrash(throwable: Throwable)
}