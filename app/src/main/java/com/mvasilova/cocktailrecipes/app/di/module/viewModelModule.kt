package com.mvasilova.cocktailrecipes.app.di.module

import com.mvasilova.cocktailrecipes.app.ui.cocktailslist.CocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CocktailsViewModel(get()) }
}