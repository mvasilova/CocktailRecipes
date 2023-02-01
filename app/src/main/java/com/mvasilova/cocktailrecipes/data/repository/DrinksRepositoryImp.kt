package com.mvasilova.cocktailrecipes.data.repository

import androidx.lifecycle.Transformations
import com.mvasilova.cocktailrecipes.app.ext.observeMainThread
import com.mvasilova.cocktailrecipes.data.db.dao.FavoriteDao
import com.mvasilova.cocktailrecipes.data.db.dao.SearchDao
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.data.entity.FiltersList
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.data.network.Api
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class DrinksRepositoryImp(
    private val api: Api,
    private val favoriteDao: FavoriteDao,
    private val searchDao: SearchDao
) : DrinksRepository {

    override fun getFilterDrinksList(type: String, name: String): Single<DrinksFilter> =
        api.getFilterDrinksList(mapOf(type to name))
            .observeMainThread()

    override fun getRecentDrinks(): Single<DrinksFilter> = api.getRecentDrinks()
        .observeMainThread()

    override fun getPopularDrinks(): Single<DrinksFilter> = api.getPopularDrinks()
        .observeMainThread()

    override fun getRecipeInfoDrink(idDrink: String): Single<RecipeInfoDrink> =
        api.getRecipeInfoDrink(idDrink)
            .observeMainThread()

    override fun getSearchByNameList(nameDrink: String): Observable<DrinksFilter> =
        api.getSearchByNameList(nameDrink).debounce(400, TimeUnit.MILLISECONDS)
            .observeMainThread()

    override fun getFiltersList(type: String): Observable<FiltersList> =
        api.getFiltersList(mapOf(type to "list"))
            .observeMainThread()

    override fun getAllFavorite() = favoriteDao.getAll()

    override fun observeDrinkFavorite(drinkId: String) =
        Transformations.map(favoriteDao.observeDrinkFavorite(drinkId)) { it > 0 }

    override fun actionFavorite(favorite: Favorite) =
        Completable.fromAction { favoriteDao.actionFavorite(favorite) }
            .observeMainThread()

    override fun getAllSearch() = searchDao.getAll()

    override fun addSearch(searchHistory: SearchHistory) =
        Completable.fromAction { searchDao.addSearchHistory(searchHistory) }
            .observeMainThread()
}
