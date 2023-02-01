package com.mvasilova.cocktailrecipes.presentation.delegates

import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.ext.dpToPx
import com.mvasilova.cocktailrecipes.app.ext.setStartDrawable
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.Filter
import com.mvasilova.cocktailrecipes.databinding.ItemFiltersListBinding

fun itemFilterName(
    itemClickedListener: (Filter) -> Unit
) = adapterDelegateViewBinding<Filter, DisplayableItem, ItemFiltersListBinding>(
    { layoutInflater, root ->
        ItemFiltersListBinding.inflate(layoutInflater, root, false)
    }
) {

    binding.root.setOnClickListener {
        if (item.isMultiChoice) {
            binding.checkbox.performClick()
        } else {
            itemClickedListener.invoke(item)
        }
    }

    binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
        item.isChecked = isChecked
    }

    bind {
        binding.apply {
            tvNameFilter.text = item.name

            checkbox.isVisible = item.isMultiChoice
            checkbox.isChecked = item.isChecked

            if (item.isMultiChoice) {
                root.updatePadding(40.dpToPx)
            }

            item.drawable?.let {
                tvNameFilter.setStartDrawable(it)
            }
        }
    }
}
