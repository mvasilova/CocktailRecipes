package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrinksListFragment : Fragment(R.layout.fragment_list) {

    private val drinksListViewModel: DrinksListViewModel by viewModel()
    private val drinksListAdapter by lazy { DrinksListAdapter(::onRecipeInfoFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinksListViewModel.drinks.value = arguments?.getSerializable("list") as List<Drink>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observe(drinksListViewModel.drinks, ::handleDrinks)
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        drinksListAdapter.collection = drinks ?: listOf()
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)

        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        rvDrinks.adapter = drinksListAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = arguments?.getString("title")
    }
}

