package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.FiltersList.Filter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters.TypeDrinksFilters.*
import io.reactivex.rxjava3.core.Observable

class FilterByParametersViewModel(
    val type: TypeDrinksFilters,
    val drinksRepository: DrinksRepository
) : BaseViewModel() {

    val filters = MutableLiveData<List<Filter>>()
    var sourceList: List<Filter> = listOf()
    var query = ""

    init {
        loadFilters()
    }

    fun loadFilters() {
        drinksRepository.getFiltersList(type.param)
            .doOnSubscribe { state.value = State.Loading }
            .flatMap { Observable.fromIterable(it.drinks) }
            .map {
                when (type) {
                    ALCOHOL -> Filter(it.strAlcoholic.orEmpty())
                    CATEGORY -> Filter(it.strCategory.orEmpty())
                    GLASS -> Filter(it.strGlass.orEmpty())
                    INGREDIENTS -> Filter(it.strIngredient1.orEmpty(), true)
                }
            }
            .toList()
            .map {
                it.apply {
                    sortBy { it.name }
                }.filter { it.name.isNotEmpty() }
            }
            .subscribe({
                state.value = State.Loaded
                sourceList = it
                filters.value = it
            }, {
                state.value = State.Error(it)
            }).addToDisposables()
    }

    fun filterBySearch(query: String) {
        val list = sourceList
            .filter {
                it.name.contains(query, true)
            }
        filters.value = list
    }

}