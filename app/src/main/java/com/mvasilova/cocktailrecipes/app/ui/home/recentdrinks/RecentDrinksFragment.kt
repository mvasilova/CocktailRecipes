package com.mvasilova.cocktailrecipes.app.ui.home.recentdrinks

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
import kotlinx.android.synthetic.main.fragment_list_preview_horizontal.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecentDrinksFragment : Fragment(R.layout.fragment_list_preview_horizontal) {

    private val recentDrinksViewModel: RecentDrinksViewModel by viewModel()
    private val previewDrinksCategoryAdapter by lazy {
        PreviewDrinksCategoryAdapter(::onRecipeInfoFragment).apply {
            titleIsVisible = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNameCategory.text = getString(R.string.title_recent)

        setupRecyclerView()
        observe(recentDrinksViewModel.state, ::handleState)
        observe(recentDrinksViewModel.recentDrinks, ::handleRecentDrinks)

    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleRecentDrinks(recentDrinks: DrinksFilter?) {
        previewDrinksCategoryAdapter.collection = recentDrinks!!.drinks
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        rvDrinks.adapter = previewDrinksCategoryAdapter
        indicator.attachToRecyclerView(rvDrinks)
    }

}