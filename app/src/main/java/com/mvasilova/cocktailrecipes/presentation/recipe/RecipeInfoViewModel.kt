package com.mvasilova.cocktailrecipes.presentation.recipe

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleError
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class RecipeInfoViewModel(
    private val drinksRepository: DrinksRepository,
    val idDrink: String
) : BaseViewModel() {

    val recipe = MutableLiveData<RecipeInfoDrink>()
    val isFavorite = drinksRepository.observeDrinkFavorite(idDrink)

    init {
        drinksRepository.getRecipeInfoDrink(idDrink).handleState(state)
            .subscribe { it ->
                recipe.value = it
            }.addToDisposables()
    }

    fun changeFavorite() {
        drinksRepository.actionFavorite(FavoriteConverter.convertRecipeInfoDrinkToFavorite(recipe.value))
            .handleError(state)
            .subscribe()
            .addToDisposables()
    }
}
