package com.mvasilova.cocktailrecipes.data.network

import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.data.entity.FiltersList
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("filter.php?c=Cocktail")
    fun getCocktailsList(): Single<DrinksFilter>

    @GET("filter.php?c=Shot")
    fun getShotsList(): Single<DrinksFilter>

    @GET("filter.php?c=Beer")
    fun getBeersList(): Single<DrinksFilter>

    @GET("lookup.php?")
    fun getRecipeInfoDrink(@Query("i") idDrink: String): Single<RecipeInfoDrink>

    @GET("search.php?")
    fun getSearchByNameList(@Query("s") nameDrink: String): Single<DrinksFilter>

    @GET("recent.php")
    fun getRecentDrinks(): Single<DrinksFilter>

    @GET("popular.php")
    fun getPopularDrinks(): Single<DrinksFilter>

    @GET("list.php?")
    fun getFiltersList(@QueryMap(encoded = true) type: Map<String, String>): Observable<FiltersList>
}