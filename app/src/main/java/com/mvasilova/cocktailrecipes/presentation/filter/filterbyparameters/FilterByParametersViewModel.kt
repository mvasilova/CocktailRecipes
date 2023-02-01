package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.entity.Filter
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters.*
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import io.reactivex.rxjava3.core.Observable
import java.util.*

class FilterByParametersViewModel(
    val type: TypeDrinksFilters,
    private val drinksRepository: DrinksRepository
) : BaseViewModel() {

    val filters = MutableLiveData<List<Filter>>()
    var sourceList: List<Filter> = listOf()

    init {
        loadFilters()
    }

    private fun loadFilters() {
        drinksRepository.getFiltersList(type.param).handleState(state)
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
                    sortBy { it.name.lowercase(Locale.getDefault()) }
                }.filter { it.name.isNotEmpty() }
            }
            .subscribe { it ->
                sourceList = it
                filters.value = it
            }.addToDisposables()
    }

    fun filterBySearch(query: String) {
        sourceList
            .filter { it.name.contains(query, true) }
            .let { filters.value = it }
    }
}
