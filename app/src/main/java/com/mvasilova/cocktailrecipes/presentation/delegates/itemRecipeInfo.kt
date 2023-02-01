package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.databinding.ItemDrinkRecipeInfoBinding

fun itemRecipeInfo() =
    adapterDelegateViewBinding<RecipeInfo, DisplayableItem, ItemDrinkRecipeInfoBinding>(
        { layoutInflater, root ->
            ItemDrinkRecipeInfoBinding.inflate(layoutInflater, root, false)
        }
    ) {

        bind {
            binding.apply {
                tvRecipeInfo.text = item.name
            }
        }
    }

data class RecipeInfo(val name: String) : DisplayableItem {
    override val itemId: String
        get() = name
}
