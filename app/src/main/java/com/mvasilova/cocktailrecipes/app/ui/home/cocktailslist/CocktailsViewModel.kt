package com.mvasilova.cocktailrecipes.app.ui.home.cocktailslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class CocktailsViewModel(drinksRepository: DrinksRepository) : BaseViewModel() {

    val cocktails = MutableLiveData<DrinksFilter>()
    init {//later clean subs
        drinksRepository.getCocktailsList()
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                cocktails.value = it
            }, {
                state.value = State.Error(it)
        }).addToDisposables()
    }

}