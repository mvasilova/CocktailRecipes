package com.mvasilova.cocktailrecipes.presentation.delegates

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.databinding.ItemSearchDrinksBinding

fun itemSearchDrinks(itemClickListener: (String) -> Unit) =
    adapterDelegateViewBinding<Drink, DisplayableItem, ItemSearchDrinksBinding>({ layoutInflater, root ->
        ItemSearchDrinksBinding.inflate(layoutInflater, root, false)
    }) {

        binding.root.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

        bind {
            binding.apply {
                GlideApp.with(context)
                    .load(item.strDrinkThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .optionalCenterCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .into(ivImage)
                tvName.text = item.strDrink
            }
        }
    }
