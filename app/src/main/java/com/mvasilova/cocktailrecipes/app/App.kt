package com.mvasilova.cocktailrecipes.app

import android.app.Application
import com.mvasilova.cocktailrecipes.app.di.module.networkModule
import com.mvasilova.cocktailrecipes.app.di.module.repositoryModule
import com.mvasilova.cocktailrecipes.app.di.module.roomModule
import com.mvasilova.cocktailrecipes.app.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(networkModule, repositoryModule, roomModule, viewModelModule))
        }
    }
}