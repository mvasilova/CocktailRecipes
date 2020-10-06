package com.mvasilova.cocktailrecipes.app

import android.app.Application
import com.mvasilova.cocktailrecipes.app.di.module.*
import com.mvasilova.cocktailrecipes.app.ext.applyTheme
import com.mvasilova.cocktailrecipes.app.ext.isUsingNightModeResources
import com.mvasilova.cocktailrecipes.data.enums.AppTheme
import com.mvasilova.cocktailrecipes.data.storage.Pref
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import javax.inject.Inject

class App : Application() {

    val pref: Pref by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    roomModule,
                    storageModule,
                    viewModelModule
                )
            )
        }

        pref.appTheme?.applyTheme()
    }
}