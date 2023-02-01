package com.mvasilova.cocktailrecipes.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FiltersList(
    @SerializedName("drinks")
    val drinks: List<Drink>?
) : Serializable {
    data class Drink(

        @SerializedName("strAlcoholic")
        val strAlcoholic: String?,
        @SerializedName("strCategory")
        val strCategory: String?,
        @SerializedName("strGlass")
        val strGlass: String?,
        @SerializedName("strIngredient1")
        val strIngredient1: String?
    ) : Serializable
}
