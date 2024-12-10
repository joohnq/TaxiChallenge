package com.joohhq.taxichallenge.di

import com.joohhq.taxichallenge.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun App.koinInit() {
    startKoin {
        androidLogger()
        androidContext(this@koinInit)
        modules(appModule, viewModelModule, networkModule, repositoryModule)
    }
}