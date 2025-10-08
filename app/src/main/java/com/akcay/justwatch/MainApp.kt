package com.akcay.justwatch

import android.app.Application
import com.akcay.justwatch.di.initKoin
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.RetentionManager

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
        initializeChucker()
    }

    private fun initializeChucker() {
        ChuckerCollector(
            context = this,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }
}