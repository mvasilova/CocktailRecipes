package com.mvasilova.cocktailrecipes.domain.repository

import com.mvasilova.cocktailrecipes.data.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DrinksRepository(val api: Api) {
    fun getCocktailsList()= api.getCocktailsList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getShotsList() = api.getShotsList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getBeersList() = api.getBeersList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getRecipeInfoDrink(idDrink: String) = api.getRecipeInfoDrink(idDrink)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}