package com.mvasilova.cocktailrecipes.presentation.searchbyname

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.ext.handleError
import com.mvasilova.cocktailrecipes.app.ext.handleState
import com.mvasilova.cocktailrecipes.app.platform.BaseViewModel
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import java.util.*

class SearchByNameViewModel(val drinksRepository: DrinksRepository) : BaseViewModel() {

    var drinks = MutableLiveData<List<Drink>>()
    var favorites = drinksRepository.getAllFavorite()

    fun getSearchByNameList(nameDrink: String) {
        drinksRepository.getSearchByNameList(nameDrink)
            .handleState(state)
            .subscribe { it ->
                drinks.value = it.drinks.sortedBy { it.strDrink?.toLowerCase(Locale.getDefault()) }
            }.addToDisposables()
    }

    fun changeFavorite(drink: Drink) {
        drinksRepository.actionFavorite(FavoriteConverter.convertDrinkToFavorite(drink))
            .handleError(state)
            .subscribe()
            .addToDisposables()
    }

    fun updateItems(favorites: List<Favorite>?) {
        val list = drinks.value
        list?.forEach { drink ->
            drink.isFavorite = favorites?.find { it.id == drink.idDrink } != null
        }
        drinks.value = list
    }
}