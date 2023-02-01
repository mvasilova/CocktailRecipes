package com.mvasilova.cocktailrecipes.presentation.delegates

import androidx.annotation.StringRes
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.databinding.ItemListPreviewCategoryBinding
import java.io.Serializable

fun homePreviewCategoryDelegate(
    itemClickedListener: (String) -> Unit,
    buttonClickListener: (PreviewCategory) -> Unit
) = adapterDelegateViewBinding<PreviewCategory, DisplayableItem, ItemListPreviewCategoryBinding>(
    { layoutInflater, root ->
        ItemListPreviewCategoryBinding.inflate(layoutInflater, root, false)
    }
) {

    val itemPreviewCategoryAdapter by lazy {
        ListDelegationAdapter(
            itemPreviewDrinksDelegate(false) {
                itemClickedListener.invoke(it)
            }
        )
    }

    binding.apply {
        buttonOpenList.setOnClickListener {
            buttonClickListener.invoke(item)
        }

        rvDrinks.layoutManager = GridLayoutManager(context, 3)
        rvDrinks.adapter = itemPreviewCategoryAdapter
    }

    bind {
        binding.apply {
            tvNameCategory.text = getString(item.title)
            itemPreviewCategoryAdapter.setData(item.list.drinks.take(6))
        }
    }
}

data class PreviewCategory(@StringRes val title: Int, val list: DrinksFilter) :
    Serializable,
    DisplayableItem {
    override val itemId: String
        get() = title.toString()
}
