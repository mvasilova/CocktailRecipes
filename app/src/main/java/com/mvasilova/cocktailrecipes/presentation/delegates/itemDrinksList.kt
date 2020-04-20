package com.mvasilova.cocktailrecipes.presentation.delegates

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.item_preview_category.view.*

fun itemDrinksList(itemClickListener: (String) -> Unit) =
    adapterDelegateLayoutContainer<DrinksFilter.Drink, Any>(R.layout.item_drinks_list) {

        containerView.setOnClickListener { itemClickListener.invoke(item.idDrink.toString()) }

        bind {
            with(containerView) {
                GlideApp.with(containerView.context)
                    .load(item.strDrinkThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .optionalCenterCrop()
                    .into(containerView.ivImage)
                containerView.tvName.text = item.strDrink
            }
        }
    }