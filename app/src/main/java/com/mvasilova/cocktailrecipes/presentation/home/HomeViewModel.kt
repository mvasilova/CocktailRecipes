package com.mvasilova.cocktailrecipes.presentation.home

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import com.mvasilova.cocktailrecipes.presentation.delegates.HorizontalPreview
import com.mvasilova.cocktailrecipes.presentation.delegates.PreviewCategory
import io.reactivex.rxjava3.kotlin.Singles

class HomeViewModel(
    val drinksRepository: DrinksRepository
) : BaseViewModel() {

    val drinks = MutableLiveData<List<DisplayableItem>>()

    init {
        loadDrinks()
    }

    fun loadDrinks() {
        Singles.zip(
            drinksRepository.getRecentDrinks(),
            drinksRepository.getPopularDrinks(),
            drinksRepository.getFilterDrinksList(
                "c",
                "cocktail"
            ),
            drinksRepository.getFilterDrinksList(
                "c",
                "shot"
            ),
            drinksRepository.getFilterDrinksList(
                "c",
                "beer"
            )
        ) { drinksFilter, drinksFilter2, drinksFilter3, drinksFilter4, drinksFilter5 ->
            listOf(drinksFilter, drinksFilter2, drinksFilter3, drinksFilter4, drinksFilter5)
        }.handleState(state)
            .subscribe { it ->
                drinks.value = listOf(
                    HorizontalPreview(R.string.home_title_recent, it[0]),
                    HorizontalPreview(R.string.home_title_popular, it[1]),
                    PreviewCategory(R.string.home_title_cocktails, it[2]),
                    PreviewCategory(R.string.home_title_shots, it[3]),
                    PreviewCategory(R.string.home_title_beers, it[4])
                )
            }.addToDisposables()
    }
}
