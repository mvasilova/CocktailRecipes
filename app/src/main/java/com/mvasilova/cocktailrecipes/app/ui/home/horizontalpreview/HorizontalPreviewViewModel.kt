package com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks.POPULAR
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks.RECENT
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import io.reactivex.Single

class HorizontalPreviewViewModel(
    val type: TypePreviewDrinks,
    val drinksRepository: DrinksRepository
) : BaseViewModel() {

    val drinks = MutableLiveData<DrinksFilter>()

    init {
        loadDrinks()
    }

    fun loadDrinks() {
        when (type) {
            RECENT -> drinksRepository.getRecentDrinks().observeDrinks()
            POPULAR -> drinksRepository.getPopularDrinks().observeDrinks()
        }
    }

    fun Single<DrinksFilter>.observeDrinks() =
        this.doOnSubscribe { state.value = State.Loading }
            .subscribe({
                state.value = State.Loaded
                drinks.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
}