package com.mvasilova.cocktailrecipes.presentation.drinkslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.ext.handleError
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter.convertDrinkToFavorite
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class DrinksListViewModel(
    val list: List<Drink>?,
    val type: String,
    val name: String,
    val drinksRepository: DrinksRepository
) : BaseViewModel() {

    var drinks = MutableLiveData<List<Drink>>()
    var sourceList: List<Drink> = listOf()

    var favorites = drinksRepository.getAllFavorite()

    init {
        checkDrinksList()
    }

    fun checkDrinksList() {
        if (list.isNullOrEmpty()) {
            loadDrinks()
        } else {
            drinks.value = list
            sourceList = list
        }
    }

    fun loadDrinks() {
        drinksRepository.getFilterDrinksList(type, name)
            .handleState(state)
            .subscribe { it ->
                drinks.value = it.drinks
                sourceList = it.drinks
            }.addToDisposables()
    }

    fun changeFavorite(drink: Drink) {
        drinksRepository.actionFavorite(convertDrinkToFavorite(drink))
            .handleError(state)
            .subscribe()
            .addToDisposables()
    }

    fun filterBySearch(query: String) {
        val list = sourceList
            .filter {
                it.strDrink?.contains(query, true) ?: false
            }
        drinks.value = list
    }

    fun updateItems(favorites: List<Favorite>?) {
        val list = drinks.value
        list?.forEach { drink ->
            drink.isFavorite = favorites?.find { it.id == drink.idDrink } != null
        }
        drinks.value = list
    }
}