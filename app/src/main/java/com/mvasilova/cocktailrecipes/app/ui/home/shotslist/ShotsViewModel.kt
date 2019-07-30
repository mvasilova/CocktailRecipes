package com.mvasilova.cocktailrecipes.app.ui.home.shotslist

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class ShotsViewModel(drinksRepository: DrinksRepository) : BaseViewModel() {

    val shots = MutableLiveData<DrinksFilter>()

    init {
        drinksRepository.getShotsList()
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                shots.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }
}