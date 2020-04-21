package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.data.entity.FiltersList.Filter
import com.mvasilova.cocktailrecipes.presentation.MainActivity
import com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters.TypeDrinksFilters.*
import com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters.adapters.FilterByParametersAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilterByParametersFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<FilterByParametersViewModel>() {
        parametersOf(args.type)
    }

    private val args: FilterByParametersFragmentArgs by navArgs()

    private val filterByParametersAdapter by lazy {
        FilterByParametersAdapter(::onDrinksListFragment)
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
        observe(screenViewModel.filters, ::handleFilters)

        btnSearch.setOnClickListener {
            screenViewModel.filterBySearch("")

            if (filterByParametersAdapter.collection.any { it.isChecked }) {
                onDrinksListFragment(filterByParametersAdapter
                    .collection
                    .filter { it.isChecked }.joinToString(separator = ",") { it.name })
            } else {
                longToast(getString(R.string.toast_select_ingredients))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    private fun handleFilters(filters: List<Filter>?) {
        if (filters.isNullOrEmpty()) {
            tvMessage.text = getString(R.string.not_found)
            tvMessage.visibility = View.VISIBLE
            filterByParametersAdapter.collection = filters ?: listOf()
        } else {
            tvMessage.visibility = View.GONE
            filterByParametersAdapter.collection = filters.sortedByDescending { it.isChecked }
        }
    }


    private fun setupRecyclerView() {
        rvDrinks.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDrinks.adapter = filterByParametersAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        when (args.type) {
            ALCOHOL -> toolbar.title = getString(R.string.filter_by_alcohol)
            CATEGORY -> toolbar.title = getString(R.string.filter_by_category)
            GLASS -> toolbar.title = getString(R.string.filter_by_glass)
            INGREDIENTS -> {
                toolbar.title = getString(R.string.multi_filter_by_ingredients)
                btnSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun onDrinksListFragment(nameFilter: String?) {
        val action =
            FilterByParametersFragmentDirections.actionFilterByParametersFragmentToDrinksListFragment(
                args.type.param,
                nameFilter!!
            )
        findNavController().navigate(action)
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

