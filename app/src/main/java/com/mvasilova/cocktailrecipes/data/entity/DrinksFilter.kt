package com.mvasilova.cocktailrecipes.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinksFilter(
    val drinks: List<Drink>
) : Parcelable {
    @Parcelize
    data class Drink(
        val idDrink: String?,
        val strDrink: String?,
        val strDrinkThumb: String?
    ) : Parcelable
}