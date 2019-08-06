package com.mvasilova.cocktailrecipes.app.di.module

import com.mvasilova.cocktailrecipes.app.ui.drinkslist.DrinksListViewModel
import com.mvasilova.cocktailrecipes.app.ui.home.beerslist.BeersViewModel
import com.mvasilova.cocktailrecipes.app.ui.home.cocktailslist.CocktailsViewModel
import com.mvasilova.cocktailrecipes.app.ui.home.shotslist.ShotsViewModel
import com.mvasilova.cocktailrecipes.app.ui.recipe.RecipeInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CocktailsViewModel(get()) }
    viewModel { ShotsViewModel(get()) }
    viewModel { BeersViewModel(get()) }

    viewModel { (idDrink: String) -> RecipeInfoViewModel(get(), idDrink) }
    viewModel { DrinksListViewModel() }


}