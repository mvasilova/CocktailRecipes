package com.mvasilova.cocktailrecipes.app.di.module

import android.content.Context
import com.mvasilova.cocktailrecipes.data.db.AppDatabase
import org.koin.dsl.module

val roomModule = module {

    single { buildRoom(get()) }
    single { getFavoriteDao(get()) }
    single { getSearchDao(get()) }
}

private fun buildRoom(context: Context) = AppDatabase.buildDataSource(context)

private fun getFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()

private fun getSearchDao(appDatabase: AppDatabase) = appDatabase.searchDao()