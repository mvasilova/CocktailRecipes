package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
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
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                drinks.value = it.drinks
                sourceList = it.drinks
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }

    fun filterBySearch(query: String) {
        val list = sourceList
            .filter {
                it.strDrink?.toLowerCase()?.contains(query.toLowerCase()) ?: false
            }
        drinks.value = list
    }
}