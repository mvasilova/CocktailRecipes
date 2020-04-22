package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import kotlinx.android.synthetic.main.item_drink_recipe_title.view.*

fun itemRecipeTitle() =
    adapterDelegateLayoutContainer<RecipeTitle, DisplayableItem>(R.layout.item_drink_recipe_title) {


        bind {
            with(containerView) {
                tvRecipeTitle.text = item.name
            }
        }
    }

data class RecipeTitle(val name: String) : DisplayableItem {
    override val itemId: String
        get() = name
}