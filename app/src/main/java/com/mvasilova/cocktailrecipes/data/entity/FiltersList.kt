package com.mvasilova.cocktailrecipes.data.entity


import com.google.gson.annotations.SerializedName

data class FiltersList(
    @SerializedName("drinks")
    val drinks: List<Drink>?
) {
    data class Drink(

        @SerializedName("strAlcoholic")
        val strAlcoholic: String?,
        @SerializedName("strCategory")
        val strCategory: String?,
        @SerializedName("strGlass")
        val strGlass: String?,
        @SerializedName("strIngredient1")
        val strIngredient1: String?
    )

    data class Filter(
        var name: String,
        val isMultiChoice: Boolean = false,
        var isChecked: Boolean = false
    )
}