package com.joohhq.taxichallenge

import android.app.Application
import com.joohhq.taxichallenge.di.koinInit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        koinInit()
    }
}