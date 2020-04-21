package com.mvasilova.cocktailrecipes.app.di.module

import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.presentation.drinkslist.DrinksListViewModel
import com.mvasilova.cocktailrecipes.presentation.favorites.FavoritesViewModel
import com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters.FilterByParametersViewModel
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.presentation.home.HomeViewModel
import com.mvasilova.cocktailrecipes.presentation.searchbyname.SearchByNameViewModel
import com.mvasilova.cocktailrecipes.presentation.recipe.RecipeInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        HomeViewModel(get())
    }
    viewModel { (type: TypeDrinksFilters) -> FilterByParametersViewModel(type, get()) }

    viewModel { (idDrink: String) -> RecipeInfoViewModel(get(), idDrink) }
    viewModel { (list: List<Drink>, type: String, name: String) ->
        DrinksListViewModel(
            list,
            type,
            name,
            get()
        )
    }
    viewModel { FavoritesViewModel(get()) }
    viewModel { SearchByNameViewModel(get()) }
}