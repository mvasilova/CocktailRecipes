package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import com.mvasilova.cocktailrecipes.databinding.ItemSearchHistoryBinding

fun itemSearchHistory(
    itemClickListener: (String) -> Unit
) = adapterDelegateViewBinding<SearchHistory, DisplayableItem, ItemSearchHistoryBinding>(
    { layoutInflater, root ->
        ItemSearchHistoryBinding.inflate(layoutInflater, root, false)
    }
) {

    binding.root.setOnClickListener { itemClickListener.invoke(item.name.toString()) }

    bind {
        binding.apply {
            tvName.text = item.name
        }
    }
}
