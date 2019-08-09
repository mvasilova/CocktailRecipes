package com.mvasilova.cocktailrecipes.app.ui.drinkslist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.MainActivity
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrinksListFragment : Fragment(R.layout.fragment_list) {

    private val drinksListViewModel: DrinksListViewModel by viewModel()
    private val drinksListAdapter by lazy { DrinksListAdapter(::onRecipeInfoFragment) }
    lateinit var searchView: SearchView
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val list = arguments?.getSerializable("list") as List<Drink>
        drinksListViewModel.drinks.value = list
        drinksListViewModel.sourceList = list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observe(drinksListViewModel.drinks, ::handleDrinks)
    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        if (drinks.isNullOrEmpty()) {
            tvMessage.text = getString(R.string.drinks_not_found)
            tvMessage.visibility = View.VISIBLE
            drinksListAdapter.collection = drinks ?: listOf()

        } else {
            tvMessage.visibility = View.GONE
            drinksListAdapter.collection = drinks
        }
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)

        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 2)
        rvDrinks.adapter = drinksListAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = arguments?.getString("title")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            actionView = searchView
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        searchView.isIconified = true
        searchView.setIconifiedByDefault(true)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                drinksListViewModel.filterBySearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                drinksListViewModel.filterBySearch(newText)
                return false
            }
        })

        if (query.isEmpty()) {
        } else {
            searchView.setQuery(query, true)
            searchView.isIconified = false
        }

        searchView.setOnCloseListener {
            false
        }
    }
}

