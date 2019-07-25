package com.mvasilova.cocktailrecipes.app.ui.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository


class RecipeInfoViewModel(
    drinksRepository: DrinksRepository,
    val idDrink: String
) : BaseViewModel() {

    val recipe = MutableLiveData<RecipeInfoDrink>()

    init {//later clean subs
        drinksRepository.getRecipeInfoDrink(idDrink)
            .subscribe({
                recipe.value = it
            }, {
                Log.d("String error", it.toString())
                it.printStackTrace()
                //add exept
                //progressbar
            }).addToDisposables()
    }


}