package com.mvasilova.cocktailrecipes.data.network

import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("filter.php?c=Cocktail")
    fun getCocktailsList(): Single<DrinksFilter>
}