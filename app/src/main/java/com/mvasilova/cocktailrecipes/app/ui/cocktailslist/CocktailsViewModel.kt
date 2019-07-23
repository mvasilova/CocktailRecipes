package com.mvasilova.cocktailrecipes.app.ui.cocktailslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class CocktailsViewModel(val drinksRepository: DrinksRepository): ViewModel() {

    val cocktails = MutableLiveData<DrinksFilter>()
    init {//later clean subs
        drinksRepository.getCocktailsList().subscribe({
            cocktails.value = it
        }, {
            //add exept
            //progressbar
        })
    }

}