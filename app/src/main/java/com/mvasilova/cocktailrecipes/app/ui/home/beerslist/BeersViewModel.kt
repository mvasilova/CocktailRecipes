package com.mvasilova.cocktailrecipes.app.ui.home.beerslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class BeersViewModel(drinksRepository: DrinksRepository) : BaseViewModel() {

    val beers = MutableLiveData<DrinksFilter>()

    init {
        drinksRepository.getBeersList()
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                beers.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }
}