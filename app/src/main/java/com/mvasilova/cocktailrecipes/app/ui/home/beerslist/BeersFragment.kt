package com.mvasilova.cocktailrecipes.app.ui.home.beerslist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.app.ui.home.adapters.PreviewDrinksCategoryAdapter
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_preview_cat.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeersFragment : Fragment(R.layout.fragment_list_preview_cat) {

    private lateinit var bundle: Bundle
    private val beersViewModel: BeersViewModel by viewModel()
    private val previewDrinksCategoryAdapter: PreviewDrinksCategoryAdapter by lazy {
        PreviewDrinksCategoryAdapter {
            onRecipeInfoFragment(
                it
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNameCategory.text = getString(R.string.title_beers)
        setupRecyclerView()
        observe(beersViewModel.state, ::handleState)
        observe(beersViewModel.beers, ::handleBeers)

        if (::bundle.isInitialized) {
            buttonOpenList.setOnClickListener {
                findNavController().navigate(R.id.action_global_drinksListFragment, bundle)
            }
        }
    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleBeers(beers: DrinksFilter?) {
        previewDrinksCategoryAdapter.collection = beers!!.drinks.takeLast(6)
        bundle = bundleOf("list" to beers.drinks)
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
