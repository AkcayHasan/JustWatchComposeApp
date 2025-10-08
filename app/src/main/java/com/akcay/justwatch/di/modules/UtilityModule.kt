package com.akcay.justwatch.di.modules

import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.ThemeManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilityModule = module {
    single { DataStoreManager(androidContext()) }
    single { ThemeManager() }
}
