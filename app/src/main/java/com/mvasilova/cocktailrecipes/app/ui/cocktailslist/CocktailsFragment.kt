package com.mvasilova.cocktailrecipes.app.ui.cocktailslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_cocktails.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailsFragment: Fragment(R.layout.fragment_list_cocktails) {

    private val cocktailsViewModel: CocktailsViewModel by viewModel()
    private val cocktailsAdapter: CocktailsAdapter by lazy { CocktailsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        //observe(cocktailsViewModel.cocktails, { handleCocktails(it)})
        observe(cocktailsViewModel.cocktails, ::handleCocktails)
    }

    private fun handleCocktails(cocktails: DrinksFilter?){
        cocktailsAdapter.collection = cocktails!!.drinks
    }

    private fun setupRecyclerView(){
        rvCocktails.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvCocktails.adapter = cocktailsAdapter
    }

}