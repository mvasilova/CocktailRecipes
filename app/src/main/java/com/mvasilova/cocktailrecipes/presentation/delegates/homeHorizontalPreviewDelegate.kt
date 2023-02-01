package com.mvasilova.cocktailrecipes.presentation.delegates

import androidx.annotation.StringRes
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.databinding.ItemListPreviewHorizontalBinding
import java.io.Serializable

fun homeHorizontalPreviewDelegate(
    itemClickedListener: (String) -> Unit
) =
    adapterDelegateViewBinding<HorizontalPreview, DisplayableItem, ItemListPreviewHorizontalBinding>(
        { layoutInflater, root ->
            ItemListPreviewHorizontalBinding.inflate(layoutInflater, root, false)
        }
    ) {

        val itemHorizontalPreviewAdapter by lazy {
            ListDelegationAdapter(
                itemPreviewDrinksDelegate(true) {
                    itemClickedListener.invoke(it)
                }
            )
        }

        binding.apply {
            rvDrinks.adapter = itemHorizontalPreviewAdapter
            indicator.attachToRecyclerView(rvDrinks)
        }

        bind {
            binding.apply {
                tvNameCategory.text = getString(item.title)
                itemHorizontalPreviewAdapter.setData(item.list.drinks)
            }
        }
    }

data class HorizontalPreview(@StringRes val title: Int, val list: DrinksFilter) :
    Serializable,
    DisplayableItem {
    override val itemId: String
        get() = title.toString()
}
