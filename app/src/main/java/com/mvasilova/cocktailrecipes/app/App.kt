package com.mvasilova.cocktailrecipes.app

import android.app.Application
import com.mvasilova.cocktailrecipes.app.di.module.appModule
import com.mvasilova.cocktailrecipes.app.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

   override fun onCreate() {
    super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(appModule, viewModelModule))
        }
    }
}