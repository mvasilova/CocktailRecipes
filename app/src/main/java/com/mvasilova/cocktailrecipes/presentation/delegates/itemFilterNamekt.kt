package com.mvasilova.cocktailrecipes.presentation.delegates

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.FiltersList
import kotlinx.android.synthetic.main.item_filters_list.view.*

fun itemFilterName(itemClickedListener: (FiltersList.Filter) -> Unit) =
    adapterDelegateLayoutContainer<FiltersList.Filter, DisplayableItem>(R.layout.item_filters_list) {

        containerView.setOnClickListener {
            if (item.isMultiChoice) {
                containerView.checkbox.performClick()
            } else {
                itemClickedListener.invoke(item)
            }
        }

        containerView.checkbox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }

        bind {
            with(containerView) {
                tvNameFilter.text = item.name

                checkbox.isVisible = item.isMultiChoice
                checkbox.isChecked = item.isChecked

                item.drawable?.let {
                    tvNameFilter.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        getDrawable(it),
                        null,
                        null,
                        null
                    )
                }
            }
        }
    }