package com.mvasilova.cocktailrecipes.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.ext.setDividerItemDecoration
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.data.entity.FiltersList.Filter
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.presentation.delegates.itemFilterName
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    val categoriesAdapter by lazy {
        ListDelegationAdapter(
            itemFilterName() {
                it.type?.let { it1 -> navigateToFilterByParameters(it1) }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategories()
    }

    private fun setupCategories() {
        rvCategories.layoutManager = LinearLayoutManager(context)
        rvCategories.adapter = categoriesAdapter
        rvCategories.setDividerItemDecoration()
        categoriesAdapter.setData(
            listOf(
                Filter(
                    getString(R.string.filter_alcoholic),
                    drawable = R.drawable.ic_alcoholic,
                    type = TypeDrinksFilters.ALCOHOL
                ),
                Filter(
                    getString(R.string.filter_category),
                    drawable = R.drawable.ic_category,
                    type = TypeDrinksFilters.CATEGORY
                ),
                Filter(
                    getString(R.string.filter_glass),
                    drawable = R.drawable.ic_glass,
                    type = TypeDrinksFilters.GLASS
                ),
                Filter(
                    getString(R.string.filter_ingredients),
                    drawable = R.drawable.ic_ingredients,
                    type = TypeDrinksFilters.INGREDIENTS
                )
            )
        )
    }

    fun navigateToFilterByParameters(type: TypeDrinksFilters) {
        val action = FilterFragmentDirections.actionFilterFragmentToFilterByParametersFragment(type)
        findNavController().navigate(action)
    }
}