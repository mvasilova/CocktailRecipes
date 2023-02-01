package com.mvasilova.cocktailrecipes.data.db.converters

import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink

object FavoriteConverter {

    fun convertDrinkToFavorite(drink: Drink) = Favorite(
        drink.idDrink.toString(),
        drink.strDrink,
        drink.strDrinkThumb
    )

    fun convertRecipeInfoDrinkToFavorite(drink: RecipeInfoDrink?) = Favorite(
        drink?.drinks?.get(0)?.idDrink.toString(),
        drink?.drinks?.get(0)?.strDrink,
        drink?.drinks?.get(0)?.strDrinkThumb
    )

    fun convertFavoriteToDrink(favorite: Favorite) = Drink(
        favorite.id,
        favorite.name,
        favorite.drinkThumb
    )
}
