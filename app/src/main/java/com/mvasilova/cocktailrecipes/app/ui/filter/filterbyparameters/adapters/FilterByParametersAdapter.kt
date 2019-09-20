package com.mvasilova.cocktailrecipes.app.ui.filter.filterbyparameters.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.data.entity.FiltersList.Filter
import kotlinx.android.synthetic.main.item_filters_list.view.*

class FilterByParametersAdapter(val clickListener: (String?) -> Unit) :
    RecyclerView.Adapter<FilterByParametersAdapter.ViewHolder>() {

    var collection: List<Filter> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_filters_list))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Filter) {


            if (item.isMultiChoice) {
                itemView.checkbox.visibility = View.VISIBLE
            } else {
                itemView.checkbox.visibility = View.GONE
            }

            item.isChecked = itemView.checkbox.isChecked

            itemView.tvNameFilter.text = item.name
            itemView.setOnClickListener { clickListener.invoke(item.name) }
        }
    }
}
