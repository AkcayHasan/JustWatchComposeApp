package com.akcay.justwatch

import android.app.Application
import com.akcay.justwatch.di.initKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}