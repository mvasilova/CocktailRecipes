package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.dpToPx
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.ext.setDividerItemDecoration
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.data.entity.Filter
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters.*
import com.mvasilova.cocktailrecipes.presentation.MainActivity
import com.mvasilova.cocktailrecipes.presentation.delegates.itemFilterName
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilterByParametersFragment : BaseFragment(R.layout.fragment_list) {

    override val screenViewModel by viewModel<FilterByParametersViewModel>() {
        parametersOf(args.type)
    }

    private val args: FilterByParametersFragmentArgs by navArgs()

    val filtersAdapter by lazy {
        ListDelegationAdapter(
            itemFilterName() {
                onDrinksListFragment(it.name)
            })
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
            val items = filtersAdapter.items as? List<Filter>

            if (items?.any { it.isChecked } == true) {
                onDrinksListFragment(items.filter { it.isChecked }
                    .joinToString(separator = ",") { it.name })
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
        filters?.let { list ->
            tvMessage.text = getString(R.string.not_found)
            tvMessage.isVisible = list.isNullOrEmpty()
            filtersAdapter.setData(list.sortedByDescending { it.isChecked })
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDrinks.adapter = filtersAdapter
        rvDrinks.setDividerItemDecoration()
        rvDrinks.setPadding(0.dpToPx)
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

    private fun onDrinksListFragment(nameFilter: String) {
        val action =
            FilterByParametersFragmentDirections.actionFilterByParametersFragmentToDrinksListFragment(
                args.type.param,
                nameFilter
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

