package com.mvasilova.cocktailrecipes.presentation.favorites

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class FavoritesViewModel(drinksRepository: DrinksRepository) : ViewModel() {

    val favoritesDB = drinksRepository.getAllFavorite()
    val favorites = MutableLiveData<List<Favorite>>()
    val mediator = MediatorLiveData<List<Favorite>>()
    var sourceList: List<Favorite> = listOf()
    var query = ""

    init {
        mediator.addSource(favoritesDB) {
            sourceList = it ?: listOf()
            filterBySearch(query)
        }
    }

    fun filterBySearch(query: String) {
        val list = sourceList
            .filter {
                it.name?.contains(query, true) ?: false
            }
        favorites.value = list
    }
}