package com.mvasilova.cocktailrecipes.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.FavoritesDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setCustomSpanSizeLookup
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.ext.setOnTextChangeListener
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter.convertFavoriteToDrink
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter
import com.mvasilova.cocktailrecipes.presentation.delegates.itemAlphabet
import com.mvasilova.cocktailrecipes.presentation.delegates.itemDrinksList
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_toolbar_search_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<FavoritesViewModel>()

    override val toolbarTitle: String
        get() = getString(R.string.favorites)

    override val setToolbar: Boolean
        get() = true

    override val setDisplayHomeAsUpEnabled: Boolean
        get() = false

    val favoritesAdapter by lazy {
        ListDelegationAdapter(
            itemDrinksList({
                val action = FavoritesDirections.actionGlobalRecipeInfoFragment(it)
                findNavController().navigate(action)
            }, {
                screenViewModel.changeFavorite(it)
            }),
            itemAlphabet()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupRecyclerView()

        observe(screenViewModel.favorites, ::handleFavorites)
        observe(screenViewModel.mediator, {})

    }

    private fun handleFavorites(favorite: List<Favorite>?) {
        favorite?.let { list ->
            tvMessage.text = getString(R.string.not_found)
            tvMessage.isVisible = favorite.isNullOrEmpty()

            val items = mutableListOf<DisplayableItem>()
            list.groupBy { it.name.orEmpty().first().toUpperCase() }.forEach {
                items.add(AlphabetLetter(it.key.toString()))
                it.value.forEach { favorite ->
                    val drink = convertFavoriteToDrink(favorite)
                    drink.isFavorite = true
                    items.add(drink)
                }
            }
            favoritesAdapter.setData(items)
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        (rvDrinks.layoutManager as? GridLayoutManager)?.setCustomSpanSizeLookup(
            favoritesAdapter, 2, 1
        )
        rvDrinks.adapter = favoritesAdapter
    }

    private fun setupSearchView() {
        searchView.setOnTextChangeListener {
            screenViewModel.filterBySearch(it)
        }
    }
}

