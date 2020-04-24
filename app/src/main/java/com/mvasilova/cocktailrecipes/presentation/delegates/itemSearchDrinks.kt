package com.mvasilova.cocktailrecipes.presentation.delegates

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import kotlinx.android.synthetic.main.item_search_drinks.view.*

fun itemSearchDrinks(itemClickListener: (String) -> Unit) =
    adapterDelegateLayoutContainer<Drink, DisplayableItem>(R.layout.item_search_drinks) {

        containerView.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

        bind {
            with(containerView) {
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