package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import kotlinx.android.synthetic.main.item_drink_recipe_info.view.*

fun itemRecipeInfo() =
    adapterDelegateLayoutContainer<RecipeInfo, DisplayableItem>(R.layout.item_drink_recipe_info) {


        bind {
            with(containerView) {
                tvRecipeInfo.text = item.name
            }
        }
    }

data class RecipeInfo(val name: String) : DisplayableItem {
    override val itemId: String
        get() = name
}