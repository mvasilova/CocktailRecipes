package com.mvasilova.cocktailrecipes.app.ui.home.recentdrinks

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class RecentDrinksViewModel(drinksRepository: DrinksRepository) : BaseViewModel() {

    val recentDrinks = MutableLiveData<DrinksFilter>()

    init {
        drinksRepository.getRecentDrinks()
            .doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                recentDrinks.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }

}