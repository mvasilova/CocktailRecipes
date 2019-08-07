package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter

class DrinksListViewModel : ViewModel() {

    var drinks = MutableLiveData<List<DrinksFilter.Drink>>()

}