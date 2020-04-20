package com.mvasilova.cocktailrecipes.presentation.home.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.app.platform.BaseViewHolder
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.item_preview_category.view.*

class PreviewDrinksCategoryAdapter(val clickListener: (String?) -> Unit) :
    RecyclerView.Adapter<PreviewDrinksCategoryAdapter.ViewHolder>() {

    var titleIsVisible: Boolean = false
    var collection: List<DrinksFilter.Drink> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_preview_category))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(override val containerView: View) :
        BaseViewHolder<DrinksFilter.Drink>(containerView) {

        override fun bind(item: DrinksFilter.Drink) {

            GlideApp.with(containerView.context)
                .load(item.strDrinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(containerView.ivImage)

            containerView.tvName.visibility = if (titleIsVisible) View.VISIBLE else View.GONE
            containerView.tvName.text = item.strDrink

            containerView.setOnClickListener { clickListener.invoke(item.idDrink) }
        }
    }
}