package com.mvasilova.cocktailrecipes.app.ui.home.cocktailslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.app.ui.home.adapters.PreviewDrinksCategoryAdapter
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_drinks.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailsFragment : Fragment(R.layout.fragment_list_drinks) {

    private val cocktailsViewModel: CocktailsViewModel by viewModel()
    private val previewDrinksCategoryAdapter: PreviewDrinksCategoryAdapter by lazy {
        PreviewDrinksCategoryAdapter {
            onRecipeInfoFragment(
                it
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNameCategory.text = getString(R.string.title_cocktails)
        setupRecyclerView()
        observe(cocktailsViewModel.state, ::handleState)
        observe(cocktailsViewModel.cocktails, ::handleCocktails)
    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleCocktails(cocktails: DrinksFilter?) {
        previewDrinksCategoryAdapter.collection = cocktails!!.drinks.takeLast(6)
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)

        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 3)
        rvDrinks.adapter = previewDrinksCategoryAdapter
    }

}