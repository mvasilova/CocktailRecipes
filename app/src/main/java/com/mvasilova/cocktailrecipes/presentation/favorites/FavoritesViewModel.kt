package com.mvasilova.cocktailrecipes.presentation.favorites

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleError
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class FavoritesViewModel(private val drinksRepository: DrinksRepository) : BaseViewModel() {

    val favoritesDB = drinksRepository.getAllFavorite()
    val favorites = MutableLiveData<List<Favorite>>()
    val mediator = MediatorLiveData<List<Favorite>>()
    var sourceList: List<Favorite> = listOf()

    init {
        mediator.addSource(favoritesDB) {
            sourceList = it ?: listOf()
            filterBySearch()
        }
    }

    fun filterBySearch(query: String = "") {
        val list = sourceList
            .filter {
                it.name?.contains(query, true) ?: false
            }
        favorites.value = list.sortedBy { it.name }
    }

    fun changeFavorite(drink: DrinksFilter.Drink) {
        drinksRepository.actionFavorite(FavoriteConverter.convertDrinkToFavorite(drink))
            .handleError(state)
            .subscribe()
            .addToDisposables()
    }
}