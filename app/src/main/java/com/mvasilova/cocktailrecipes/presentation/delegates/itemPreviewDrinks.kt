package com.mvasilova.cocktailrecipes.presentation.delegates

import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.databinding.ItemPreviewCategoryBinding

fun itemPreviewDrinksDelegate(
    titleIsVisible: Boolean,
    itemClickListener: (String) -> Unit
) = adapterDelegateViewBinding<DrinksFilter.Drink, Any, ItemPreviewCategoryBinding>(
    { layoutInflater, root ->
        ItemPreviewCategoryBinding.inflate(layoutInflater, root, false)
    }
) {

    binding.root.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

    bind {
        binding.apply {
            GlideApp.with(context)
                .load(item.strDrinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(ivImage)

            tvName.visibility = if (titleIsVisible) View.VISIBLE else View.GONE
            tvName.text = item.strDrink
        }
    }
}
