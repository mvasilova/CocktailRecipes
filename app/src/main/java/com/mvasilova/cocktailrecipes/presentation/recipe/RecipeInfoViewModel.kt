package com.mvasilova.cocktailrecipes.presentation.recipe

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository


class RecipeInfoViewModel(
    val drinksRepository: DrinksRepository,
    val idDrink: String
) : BaseViewModel() {

    val recipe = MutableLiveData<RecipeInfoDrink>()
    val isFavorite = drinksRepository.observeDrinkFavorite(idDrink)

    init {
        drinksRepository.getRecipeInfoDrink(idDrink)
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                recipe.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }

    fun changeFavorite() {
        val recipeInfoDrink = recipe.value

        if (recipeInfoDrink != null) {
            val favorite =
                Favorite(
                    recipeInfoDrink.drinks!![0].idDrink!!,
                    recipeInfoDrink.drinks[0].strDrink!!,
                    recipeInfoDrink.drinks[0].strDrinkThumb!!
                )

            drinksRepository.actionFavorite(favorite).subscribe()
        }

    }
}