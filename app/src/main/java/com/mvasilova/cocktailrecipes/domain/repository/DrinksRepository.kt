package com.mvasilova.cocktailrecipes.domain.repository

import androidx.lifecycle.Transformations
import com.mvasilova.cocktailrecipes.app.ext.observeMainThread
import com.mvasilova.cocktailrecipes.data.db.FavoriteDao
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import com.mvasilova.cocktailrecipes.data.network.Api

class DrinksRepository(val api: Api, val favoriteDao: FavoriteDao) {
    fun getCocktailsList() = api.getCocktailsList()
        .observeMainThread()

    fun getShotsList() = api.getShotsList()
        .observeMainThread()

    fun getBeersList() = api.getBeersList()
        .observeMainThread()

    fun getRecipeInfoDrink(idDrink: String) = api.getRecipeInfoDrink(idDrink)
        .observeMainThread()

    fun getAllFavorite() = favoriteDao.getAll()

    fun insertFavorite(favorite: Favorite) = favoriteDao.insertDrink(favorite)
        .observeMainThread()

    fun deleteFavorite(idDrink: String) = favoriteDao.deleteDrink(idDrink)
        .observeMainThread()

    fun observeDrinkFavorite(drinkId: String) =
        Transformations.map(favoriteDao.observeDrinkFavorite(drinkId)) { it > 0 }

    fun actionFavorite(favorite: Favorite) = favoriteDao.isDrinkFavorite(favorite.id)
        .observeMainThread()
        .map { it > 0 }
        .flatMap {
            if (it)
                deleteFavorite(favorite.id)
            else
                insertFavorite(favorite)
        }
}