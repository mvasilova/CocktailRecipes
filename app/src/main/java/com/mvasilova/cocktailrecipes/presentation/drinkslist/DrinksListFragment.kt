package com.mvasilova.cocktailrecipes.presentation.drinkslist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setCustomSpanSizeLookup
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.presentation.MainActivity
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter
import com.mvasilova.cocktailrecipes.presentation.delegates.PreviewCategory
import com.mvasilova.cocktailrecipes.presentation.delegates.itemAlphabet
import com.mvasilova.cocktailrecipes.presentation.delegates.itemDrinksList
import com.mvasilova.cocktailrecipes.presentation.home.HomeFragment.Companion.PREVIEW_CATEGORY_KEY
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DrinksListFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<DrinksListViewModel>() {
        parametersOf(previewCategory?.list?.drinks, args.type, args.name)
    }

    private val args: DrinksListFragmentArgs by navArgs()
    private val previewCategory by lazy { arguments?.getSerializable(PREVIEW_CATEGORY_KEY) as? PreviewCategory }

    val drinksListAdapter by lazy {
        ListDelegationAdapter(
            itemDrinksList({
                val action =
                    DrinksListFragmentDirections.actionDrinksListFragmentToRecipeInfoFragment(it)
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

        observe(screenViewModel.drinks, ::handleDrinks)
        observe(screenViewModel.favorites, ::handleFavorites)
    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        drinks?.let { list ->
            tvMessage.text = getString(R.string.not_found)
            tvMessage.isVisible = list.isNullOrEmpty()

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

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        (rvDrinks.layoutManager as? GridLayoutManager)?.setCustomSpanSizeLookup(
            drinksListAdapter, 2, 1
        )
        rvDrinks.adapter = drinksListAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = previewCategory?.title?.let { getString(it) } ?: args.name
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

