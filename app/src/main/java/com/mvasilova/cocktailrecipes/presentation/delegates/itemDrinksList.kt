package com.mvasilova.cocktailrecipes.presentation.delegates

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import kotlinx.android.synthetic.main.item_drinks_list.view.*

fun itemDrinksList(itemClickListener: (String) -> Unit, favoriteClickListener: (Drink) -> Unit) =
    adapterDelegateLayoutContainer<Drink, DisplayableItem>(R.layout.item_drinks_list) {

        containerView.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

        containerView.ivFavorite.setOnClickListener { favoriteClickListener.invoke(item) }

        bind {
            with(containerView) {
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