package com.mvasilova.cocktailrecipes.app.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.FavoritesDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment(R.layout.fragment_list) {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter {
            onRecipeInfoFragment(
                it
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observe(favoritesViewModel.favorites, ::handleFavorites)

    }

    private fun handleFavorites(favorite: List<Favorite>?) {
        favoritesAdapter.collection = favorite ?: listOf()

    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = FavoritesDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)

        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        rvDrinks.adapter = favoritesAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = getString(R.string.favorites)
    }

}

