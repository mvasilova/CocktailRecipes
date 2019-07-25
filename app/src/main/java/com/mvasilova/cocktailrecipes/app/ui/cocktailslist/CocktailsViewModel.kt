package com.mvasilova.cocktailrecipes.app.ui.cocktailslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class CocktailsViewModel(drinksRepository: DrinksRepository) : BaseViewModel() {

    val cocktails = MutableLiveData<DrinksFilter>()
    init {//later clean subs
        drinksRepository.getCocktailsList().subscribe({
            cocktails.value = it
        }, {
            //add exept
            //progressbar
        }).addToDisposables()
    }

}