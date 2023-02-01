package com.mvasilova.cocktailrecipes.data.entity

import androidx.annotation.DrawableRes
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters
import java.io.Serializable

data class Filter(
    var name: String,
    val isMultiChoice: Boolean = false,
    var isChecked: Boolean = false,
    @DrawableRes val drawable: Int? = null,
    val type: TypeDrinksFilters? = null
) : Serializable, DisplayableItem {
    override val itemId: String
        get() = name
}
