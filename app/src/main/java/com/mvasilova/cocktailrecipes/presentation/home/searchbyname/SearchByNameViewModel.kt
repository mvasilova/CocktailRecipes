package com.mvasilova.cocktailrecipes.presentation.home.searchbyname

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class SearchByNameViewModel(val drinksRepository: DrinksRepository) : BaseViewModel() {

    var drinks = MutableLiveData<List<DrinksFilter.Drink>>()

    fun getSearchByNameList(nameDrink: String) {
        drinksRepository.getSearchByNameList(nameDrink)
            .subscribe({
                if (it.drinks.isNullOrEmpty()) {
                    state.value = State.Loading
                    drinks.value = it.drinks
                } else {
                    state.value = State.Loaded
                    drinks.value = it.drinks.sortedBy { it.strDrink }
                }
            }, {
                state.value = State.Error(it)
            }).addToDisposables()

    }

}