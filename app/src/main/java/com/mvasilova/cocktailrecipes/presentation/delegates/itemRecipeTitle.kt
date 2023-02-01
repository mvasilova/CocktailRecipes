package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.databinding.ItemDrinkRecipeTitleBinding

fun itemRecipeTitle() =
    adapterDelegateViewBinding<RecipeTitle, DisplayableItem, ItemDrinkRecipeTitleBinding>(
        { layoutInflater, root ->
            ItemDrinkRecipeTitleBinding.inflate(layoutInflater, root, false)
        }
    ) {

        bind {
            binding.apply {
                tvRecipeTitle.text = item.name
            }
        }
    }

data class RecipeTitle(val name: String) : DisplayableItem {
    override val itemId: String
        get() = name
}
