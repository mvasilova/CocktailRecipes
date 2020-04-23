package com.mvasilova.cocktailrecipes.presentation.searchbyname

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setCustomSpanSizeLookup
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.ext.setOnTextChangeListener
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter
import com.mvasilova.cocktailrecipes.presentation.delegates.itemAlphabet
import com.mvasilova.cocktailrecipes.presentation.delegates.itemDrinksList
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_toolbar_search_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByNameFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<SearchByNameViewModel>()

    override val setToolbar: Boolean
        get() = true

    val drinksListAdapter by lazy {
        ListDelegationAdapter(
            itemDrinksList({
                val action = HomeDirections.actionGlobalRecipeInfoFragment(it)
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

        observe(screenViewModel.drinks, ::handleDrinks)
        observe(screenViewModel.favorites, ::handleFavorites)
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        drinks?.let { list ->
            tvMessage.text = getString(R.string.not_found)
            tvMessage.isVisible = drinks.isNullOrEmpty()

            val items = mutableListOf<DisplayableItem>()
            list.groupBy { it.strDrink.orEmpty().first().toUpperCase() }.forEach {
                items.add(AlphabetLetter(it.key.toString()))
                items.addAll(it.value)
            }
            drinksListAdapter.setData(items)
        }
    }

    private fun handleFavorites(list: List<Favorite>?) {
        screenViewModel.updateItems(list)
    }

    private fun checkQuery(query: String) {
        if (query.isEmpty()) {
            screenViewModel.drinks.value = emptyList()
            showText()
        } else {
            screenViewModel.getSearchByNameList(query)
        }
    }

    private fun showText() {
        tvMessage.isVisible = searchView?.query.isNullOrEmpty()
        tvMessage.text = getString(R.string.find_drinks)
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        (rvDrinks.layoutManager as? GridLayoutManager)?.setCustomSpanSizeLookup(
            drinksListAdapter, 2, 1
        )
        rvDrinks.adapter = drinksListAdapter
    }

    private fun setupSearchView() {
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)

        searchView.queryHint = getString(R.string.search_by_name)

        searchView.setOnTextChangeListener {
            checkQuery(it)
        }
        showText()
    }

}

