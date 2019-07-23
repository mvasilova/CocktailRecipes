package com.mvasilova.cocktailrecipes.domain.repository

import com.mvasilova.cocktailrecipes.data.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers

class DrinksRepository(val api: Api) {
    fun getCocktailsList()= api.getCocktailsList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}