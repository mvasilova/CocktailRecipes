package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.inflate
import com.mvasilova.cocktailrecipes.app.platform.BaseViewHolder
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

    inner class ViewHolder(override val containerView: View) :
        BaseViewHolder<Filter>(containerView) {

        override fun bind(item: Filter) {

            if (item.isMultiChoice) {
                containerView.checkbox.visibility = View.VISIBLE
                containerView.isEnabled = false
            } else {
                containerView.checkbox.visibility = View.GONE
                containerView.isEnabled = true
            }
            containerView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            containerView.setOnClickListener { clickListener.invoke(item.name) }

            containerView.checkbox.isChecked = item.isChecked
            containerView.tvNameFilter.text = item.name
        }
    }
}
