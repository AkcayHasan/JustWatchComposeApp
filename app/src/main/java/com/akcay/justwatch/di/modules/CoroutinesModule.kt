package com.akcay.justwatch.di.modules

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutineDispatcher>(qualifier = ioDispatcher) { Dispatchers.IO }
    single<CoroutineDispatcher>(qualifier = defaultDispatcher) { Dispatchers.Default }
    single<CoroutineScope>(qualifier = applicationScope) { 
        CoroutineScope(SupervisorJob() + get<CoroutineDispatcher>(qualifier = defaultDispatcher))
    }
}

val ioDispatcher = org.koin.core.qualifier.named("IO_DISPATCHER")
val defaultDispatcher = org.koin.core.qualifier.named("DEFAULT_DISPATCHER")
val applicationScope = org.koin.core.qualifier.named("APPLICATION_SCOPE")
