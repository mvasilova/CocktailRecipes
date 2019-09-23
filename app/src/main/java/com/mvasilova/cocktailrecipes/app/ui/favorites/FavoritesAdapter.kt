package com.mvasilova.cocktailrecipes.app.ui.favorites

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.app.platform.BaseViewHolder
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import kotlinx.android.synthetic.main.item_drinks_list.view.*

class FavoritesAdapter(val clickListener: (String?) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var collection: List<Favorite> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_drinks_list))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(override val containerView: View) :
        BaseViewHolder<Favorite>(containerView) {

        override fun bind(item: Favorite) {

            GlideApp.with(containerView.context)
                .load(item.drinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(containerView.ivImage)
            containerView.tvName.text = item.name

            containerView.setOnClickListener { clickListener.invoke(item.id) }
        }
    }
}