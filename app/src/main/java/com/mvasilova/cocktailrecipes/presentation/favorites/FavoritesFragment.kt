package com.mvasilova.cocktailrecipes.presentation.favorites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.FavoritesDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setCustomSpanSizeLookup
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.converters.FavoriteConverter.convertFavoriteToDrink
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.presentation.MainActivity
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter
import com.mvasilova.cocktailrecipes.presentation.delegates.itemAlphabet
import com.mvasilova.cocktailrecipes.presentation.delegates.itemDrinksList
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<FavoritesViewModel>()

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

    lateinit var searchView: SearchView
    private var query: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observe(screenViewModel.favorites, ::handleFavorites)
        observe(screenViewModel.mediator, {})

    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    private fun handleFavorites(favorite: List<Favorite>?) {
        tvMessage.text = getString(R.string.not_found)
        tvMessage.isVisible = favorite.isNullOrEmpty()

        val list = mutableListOf<DisplayableItem>()
        favorite?.groupBy { it.name.orEmpty().first().toUpperCase() }?.forEach {
            list.add(AlphabetLetter(it.key.toString()))
            it.value.forEach { favorite ->
                val drink = convertFavoriteToDrink(favorite)
                drink.isFavorite = true
                list.add(drink)
            }
        }
        favoritesAdapter.setData(list)
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        (rvDrinks.layoutManager as? GridLayoutManager)?.setCustomSpanSizeLookup(
            favoritesAdapter, 2, 1
        )
        rvDrinks.adapter = favoritesAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = getString(R.string.favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        searchView =
            SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            actionView = searchView
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        searchView.isIconified = true
        searchView.setIconifiedByDefault(true)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                screenViewModel.filterBySearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                screenViewModel.filterBySearch(newText)
                return false
            }
        })

        if (query.isNotEmpty()) {
            searchView.setQuery(query, true)
            searchView.isIconified = false
        }

        searchView.setOnCloseListener {
            false
        }
    }
}

