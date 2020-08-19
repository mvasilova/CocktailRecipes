package com.mvasilova.cocktailrecipes.presentation.delegates

import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.item_list_preview_horizontal.view.*
import java.io.Serializable

fun homeHorizontalPreviewDelegate(itemClickedListener: (String) -> Unit) =
    adapterDelegateLayoutContainer<HorizontalPreview, DisplayableItem>(R.layout.item_list_preview_horizontal) {

        val itemHorizontalPreviewAdapter by lazy {
            ListDelegationAdapter(
                itemPreviewDrinksDelegate(true) {
                    itemClickedListener.invoke(it)
                }
            )
        }

        with(containerView) {
            rvDrinks.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvDrinks.adapter = itemHorizontalPreviewAdapter
            indicator.attachToRecyclerView(rvDrinks)
        }

        bind {
            with(containerView) {
                tvNameCategory.text = getString(item.title)
                itemHorizontalPreviewAdapter.setData(item.list.drinks)
            }
        }
    }

data class HorizontalPreview(@StringRes val title: Int, val list: DrinksFilter) : Serializable,
    DisplayableItem {
    override val itemId: String
        get() = title.toString()
}