package com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import io.reactivex.Single

class PreviewDrinksViewModel(
    val category: CategoriesPreviewDrinks,
    val drinksRepository: DrinksRepository
) : BaseViewModel() {

    val drinks = MutableLiveData<DrinksFilter>()

    init {
        loadDrinks()
    }

    fun loadDrinks() {
        when (category) {
            CategoriesPreviewDrinks.COCKTAILS -> drinksRepository.getFilterDrinksList(
                "c",
                "cocktail"
            ).observeDrinks()
            CategoriesPreviewDrinks.SHOTS -> drinksRepository.getFilterDrinksList(
                "c",
                "shot"
            ).observeDrinks()
            CategoriesPreviewDrinks.BEERS -> drinksRepository.getFilterDrinksList(
                "c",
                "beer"
            ).observeDrinks()
        }
    }

    fun Single<DrinksFilter>.observeDrinks() =
        this.doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                drinks.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()

}