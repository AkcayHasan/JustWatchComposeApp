package com.akcay.justwatch.domain.repository

interface LogRepository {

    fun logNonFatalCrash(throwable: Throwable)
}