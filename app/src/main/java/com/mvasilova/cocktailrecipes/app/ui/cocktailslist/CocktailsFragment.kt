package com.mvasilova.cocktailrecipes.app.ui.cocktailslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_drinks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailsFragment : Fragment(R.layout.fragment_list_drinks) {

    private val cocktailsViewModel: CocktailsViewModel by viewModel()
    private val cocktailsAdapter: CocktailsAdapter by lazy { CocktailsAdapter { onRecipeInfoFragment(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        //observe(cocktailsViewModel.cocktails, { handleCocktails(it)})
        observe(cocktailsViewModel.cocktails, ::handleCocktails)
    }

    private fun handleCocktails(cocktails: DrinksFilter?){
        cocktailsAdapter.collection = cocktails!!.drinks.takeLast(6)
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = CocktailsFragmentDirections.actionCocktailsFragmentToRecipeInfoFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView(){
        rvCocktails.layoutManager = GridLayoutManager(activity, 3)
        rvCocktails.adapter = cocktailsAdapter
    }

}