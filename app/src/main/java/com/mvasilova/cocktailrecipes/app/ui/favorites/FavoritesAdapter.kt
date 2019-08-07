package com.mvasilova.cocktailrecipes.app.ui.favorites

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import kotlinx.android.synthetic.main.item_list.view.*

class FavoritesAdapter(val clickListener: (String?) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var collection: List<Favorite> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_list))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Favorite) {

            GlideApp.with(itemView.context)
                .load(item.drinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(itemView.ivImage)
            itemView.tvName.text = item.name

            itemView.setOnClickListener { clickListener.invoke(item.id) }
        }
    }
}