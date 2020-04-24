package com.mvasilova.cocktailrecipes.domain.repository

import androidx.lifecycle.LiveData
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.data.entity.FiltersList
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface DrinksRepository {
    fun getFilterDrinksList(type: String, name: String): Single<DrinksFilter>
    fun getRecentDrinks(): Single<DrinksFilter>
    fun getPopularDrinks(): Single<DrinksFilter>
    fun getRecipeInfoDrink(idDrink: String): Single<RecipeInfoDrink>
    fun getSearchByNameList(nameDrink: String): Observable<DrinksFilter>
    fun getFiltersList(type: String): Observable<FiltersList>
    fun getAllFavorite(): LiveData<List<Favorite>>
    fun observeDrinkFavorite(drinkId: String): LiveData<Boolean>
    fun actionFavorite(favorite: Favorite): Completable
    fun getAllSearch(): LiveData<List<SearchHistory>>
    fun addSearch(searchHistory: SearchHistory): Completable
}