package com.mvasilova.cocktailrecipes.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeInfoDrink(
    @SerializedName("drinks")
    val drinks: List<Drink>?
) : Parcelable {
    @Parcelize
    data class Drink(
        @SerializedName("dateModified")
        val dateModified: String?,
        @SerializedName("idDrink")
        val idDrink: String?,
        @SerializedName("strAlcoholic")
        val strAlcoholic: String?,
        @SerializedName("strCategory")
        val strCategory: String?,
        @SerializedName("strDrink")
        val strDrink: String?,
        @SerializedName("strDrinkThumb")
        val strDrinkThumb: String?,
        @SerializedName("strGlass")
        val strGlass: String?,
        @SerializedName("strIBA")
        val strIBA: String?,
        @SerializedName("strInstructions")
        val strInstructions: String?,
        val ingredients: List<String>,
        val measure: List<String>
    ) : Parcelable
}
