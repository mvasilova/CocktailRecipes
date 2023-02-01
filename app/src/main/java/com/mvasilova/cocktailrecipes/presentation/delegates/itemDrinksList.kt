package com.mvasilova.cocktailrecipes.presentation.delegates

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.databinding.ItemDrinksListBinding

fun itemDrinksList(
    itemClickListener: (String) -> Unit,
    favoriteClickListener: (Drink) -> Unit
) = adapterDelegateViewBinding<Drink, DisplayableItem, ItemDrinksListBinding>(
    { layoutInflater, root ->
        ItemDrinksListBinding.inflate(layoutInflater, root, false)
    }
) {

    binding.root.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

    binding.ivFavorite.setOnClickListener { favoriteClickListener.invoke(item) }

    bind {
        binding.apply {
            GlideApp.with(context)
                .load(item.strDrinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(ivImage)
            tvName.text = item.strDrink

            if (item.isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}
