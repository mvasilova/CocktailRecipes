package com.mvasilova.cocktailrecipes.presentation.searchbyname

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleError
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import java.util.*

class SearchByNameViewModel(val drinksRepository: DrinksRepository) : BaseViewModel() {

    val drinks = MutableLiveData<List<Drink>>()
    var searchHistory = drinksRepository.getAllSearch()

    fun getSearchByNameList(nameDrink: String) {
        if (nameDrink.isNotEmpty()) {
            drinksRepository.getSearchByNameList(nameDrink)
                .handleState(state)
                .subscribe { it ->
                    drinks.value =
                        it.drinks.sortedBy { it.strDrink?.toLowerCase(Locale.getDefault()) }
                }.addToDisposables()
        } else {
            disposables.clear()
            state.value = State.Loaded
            drinks.value = emptyList()
        }
    }

    fun addSearch(name: String) {
        if (name.isNotEmpty()) {
            drinksRepository.addSearch(SearchHistory(name))
                .handleError(state)
                .subscribe()
        }
    }
}