package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import kotlinx.android.synthetic.main.item_search_history.view.*

fun itemSearchHistory(itemClickListener: (String) -> Unit) =
    adapterDelegateLayoutContainer<SearchHistory, DisplayableItem>(R.layout.item_search_history) {

        containerView.setOnClickListener { itemClickListener.invoke(item.name.toString()) }

        bind {
            with(containerView) {
                tvName.text = item.name
            }
        }
    }