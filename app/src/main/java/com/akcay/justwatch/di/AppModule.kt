package com.akcay.justwatch.di

import android.content.Context
import com.akcay.justwatch.di.modules.coroutinesModule
import com.akcay.justwatch.di.modules.firebaseModule
import com.akcay.justwatch.di.modules.networkModule
import com.akcay.justwatch.di.modules.repositoryModule
import com.akcay.justwatch.di.modules.roomModule
import com.akcay.justwatch.di.modules.useCaseModule
import com.akcay.justwatch.di.modules.utilityModule
import com.akcay.justwatch.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    includes(
        coroutinesModule,
        networkModule,
        roomModule,
        firebaseModule,
        repositoryModule,
        useCaseModule,
        utilityModule,
        viewModelModule
    )
}

fun initKoin(androidContext: Context) {
    startKoin {
        androidContext(androidContext)
        modules(appModule)
    }
}
