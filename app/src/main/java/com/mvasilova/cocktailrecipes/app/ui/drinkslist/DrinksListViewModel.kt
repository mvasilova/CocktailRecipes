package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter

class DrinksListViewModel : ViewModel() {

    var drinks = MutableLiveData<List<DrinksFilter.Drink>>()
    var sourceList: List<DrinksFilter.Drink> = listOf()


    fun filterBySearch(query: String) {
        val list = sourceList
            .filter {
                it.strDrink?.toLowerCase()?.contains(query.toLowerCase()) ?: false
            }
        drinks.value = list
    }
}