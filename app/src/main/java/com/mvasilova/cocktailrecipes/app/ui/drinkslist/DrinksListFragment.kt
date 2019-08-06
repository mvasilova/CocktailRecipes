package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrinksListFragment : Fragment(R.layout.fragment_list) {

    private val drinksListViewModel: DrinksListViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        drinksListViewModel.drinks.value = arguments?.getSerializable("list") as List<DrinksFilter>


    }


}

