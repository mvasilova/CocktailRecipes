package com.mvasilova.cocktailrecipes.data.network

import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("filter.php?c=Cocktail")
    fun getCocktailsList(): Single<DrinksFilter>

    @GET("lookup.php?")
    fun getRecipeInfoDrink(@Query("i") idDrink: String): Single<RecipeInfoDrink>
}