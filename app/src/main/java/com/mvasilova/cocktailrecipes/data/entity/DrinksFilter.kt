package com.mvasilova.cocktailrecipes.data.entity

data class DrinksFilter(
    val drinks: List<Drink>
) {
    data class Drink(
        val idDrink: String?,
        val strDrink: String?,
        val strDrinkThumb: String?
    )
}