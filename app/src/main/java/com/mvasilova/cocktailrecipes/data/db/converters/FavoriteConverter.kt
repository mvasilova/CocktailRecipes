package com.mvasilova.cocktailrecipes.data.db.converters

import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink

object FavoriteConverter {

    fun convertDrinkToFavorite(drink: Drink) = Favorite(
        drink.idDrink.toString(),
        drink.strDrink,
        drink.strDrinkThumb
    )

    fun convertFavoriteToDrink(favorite: Favorite) = Drink(
        favorite.id,
        favorite.name,
        favorite.drinkThumb
    )
}