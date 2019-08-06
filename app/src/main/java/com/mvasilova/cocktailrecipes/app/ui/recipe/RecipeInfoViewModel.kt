package com.mvasilova.cocktailrecipes.app.ui.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository


class RecipeInfoViewModel(
    val drinksRepository: DrinksRepository,
    val idDrink: String
) : BaseViewModel() {

    val recipe = MutableLiveData<RecipeInfoDrink>()
    val isFavorite = MutableLiveData<Boolean>()

    init {
        drinksRepository.getRecipeInfoDrink(idDrink)
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                recipe.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()


        drinksRepository.observeDrinkFavorite(idDrink).subscribe {
            isFavorite.value = it
        }.addToDisposables()

        drinksRepository.getAllFavorite().subscribe {
            Log.d("testdb", it.toString())
        }.addToDisposables()
    }

    fun changeFavorite() {
        val recipeInfoDrink = recipe.value

        if (recipeInfoDrink != null) {
            val favorite = Favorite(
                recipeInfoDrink.drinks!![0].idDrink!!,
                recipeInfoDrink.drinks[0].strDrink!!,
                recipeInfoDrink.drinks[0].strDrinkThumb!!
            )

            drinksRepository.changeFavorite(favorite).subscribe()
        }

    }
}