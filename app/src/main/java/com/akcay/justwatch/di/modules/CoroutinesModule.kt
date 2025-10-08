package com.akcay.justwatch.di.modules

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
