package com.mvasilova.cocktailrecipes.app.ui.recipe

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository


class RecipeInfoViewModel(
    drinksRepository: DrinksRepository,
    val idDrink: String
) : BaseViewModel() {

    val recipe = MutableLiveData<RecipeInfoDrink>()

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
}