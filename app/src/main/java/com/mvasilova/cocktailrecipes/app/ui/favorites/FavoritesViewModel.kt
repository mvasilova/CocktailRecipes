package com.mvasilova.cocktailrecipes.app.ui.favorites

import androidx.lifecycle.ViewModel
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository

class FavoritesViewModel(drinksRepository: DrinksRepository) : ViewModel() {

    val favorites = drinksRepository.getAllFavorite()

}