package com.mvasilova.cocktailrecipes.app.di.module

import com.mvasilova.cocktailrecipes.data.repository.DrinksRepositoryImp
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<DrinksRepository> { DrinksRepositoryImp(get(), get()) }
}
