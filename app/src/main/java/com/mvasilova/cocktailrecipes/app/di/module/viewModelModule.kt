package com.mvasilova.cocktailrecipes.app.di.module

import com.mvasilova.cocktailrecipes.app.ui.drinkslist.DrinksListViewModel
import com.mvasilova.cocktailrecipes.app.ui.favorites.FavoritesViewModel
import com.mvasilova.cocktailrecipes.app.ui.filter.filterbyparameters.FilterByParametersViewModel
import com.mvasilova.cocktailrecipes.app.ui.filter.filterbyparameters.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.HorizontalPreviewViewModel
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks
import com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks.CategoriesPreviewDrinks
import com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks.PreviewDrinksViewModel
import com.mvasilova.cocktailrecipes.app.ui.home.searchbyname.SearchByNameViewModel
import com.mvasilova.cocktailrecipes.app.ui.recipe.RecipeInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (category: CategoriesPreviewDrinks) -> PreviewDrinksViewModel(category, get()) }
    viewModel { (type: TypePreviewDrinks) -> HorizontalPreviewViewModel(type, get()) }
    viewModel { (type: TypeDrinksFilters) -> FilterByParametersViewModel(type, get()) }

    viewModel { (idDrink: String) -> RecipeInfoViewModel(get(), idDrink) }
    viewModel { DrinksListViewModel() }
    viewModel { FavoritesViewModel(get()) }
    viewModel { SearchByNameViewModel(get()) }
}