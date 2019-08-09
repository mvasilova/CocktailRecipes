package com.mvasilova.cocktailrecipes.app.ui.favorites

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.data.entity.Favorite
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
                it.name?.toLowerCase()?.contains(query.toLowerCase()) ?: false
            }
        favorites.value = list
    }
}