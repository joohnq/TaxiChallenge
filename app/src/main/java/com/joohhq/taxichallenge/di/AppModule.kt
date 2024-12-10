package com.joohhq.taxichallenge.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(Dispatchers::IO)
}