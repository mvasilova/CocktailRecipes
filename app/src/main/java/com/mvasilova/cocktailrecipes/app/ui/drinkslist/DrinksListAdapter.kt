package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.item_list.view.*

class DrinksListAdapter(val clickListener: (String?) -> Unit) :
    RecyclerView.Adapter<DrinksListAdapter.ViewHolder>() {

    var collection: List<DrinksFilter.Drink> = listOf()
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

        fun bind(item: DrinksFilter.Drink) {

            GlideApp.with(itemView.context)
                .load(item.strDrinkThumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .optionalCenterCrop()
                .into(itemView.ivImage)
            itemView.tvName.text = item.strDrink

            itemView.setOnClickListener { clickListener.invoke(item.idDrink) }
        }
    }
}