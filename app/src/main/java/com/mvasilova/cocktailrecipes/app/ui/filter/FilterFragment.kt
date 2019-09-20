package com.mvasilova.cocktailrecipes.app.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ui.filter.filterbyparameters.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.app.ui.filter.filterbyparameters.TypeDrinksFilters.*
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment(R.layout.fragment_filter) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        filterByAlcoholic.setOnClickListener {
            navigateToFilterByParameters(ALCOHOL)
        }

        filterByCategory.setOnClickListener {
            navigateToFilterByParameters(CATEGORY)
        }

        filterByGlass.setOnClickListener {
            navigateToFilterByParameters(GLASS)
        }

        filterByIngredients.setOnClickListener {
            navigateToFilterByParameters(INGREDIENTS)
        }
    }

    fun navigateToFilterByParameters(type: TypeDrinksFilters) {
        val action = FilterFragmentDirections.actionFilterFragmentToFilterByParametersFragment(type)
        findNavController().navigate(action)
    }
}