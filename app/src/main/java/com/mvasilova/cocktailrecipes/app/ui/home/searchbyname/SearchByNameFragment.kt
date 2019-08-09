package com.mvasilova.cocktailrecipes.app.ui.home.searchbyname

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.app.ui.drinkslist.DrinksListAdapter
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByNameFragment : Fragment(R.layout.fragment_list) {

    private val searchByNameViewModel: SearchByNameViewModel by viewModel()
    private val drinksListAdapter by lazy { DrinksListAdapter(::onRecipeInfoFragment) }
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

        observe(searchByNameViewModel.drinks, ::handleDrinks)
        observe(searchByNameViewModel.state, ::handleState)
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        drinksListAdapter.collection = drinks ?: listOf()
    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> tvMessage.apply {
                visibility = View.VISIBLE
                text = getString(R.string.drinks_not_found)
            }
            State.Loaded -> tvMessage.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            actionView = searchView
            this.expandActionView()
        }

        searchView.queryHint = getString(R.string.search_by_name)
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                checkQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                checkQuery(newText)
                return false
            }
        })

        searchView.setQuery(query, true)

        searchView.setOnCloseListener {
            showText()
            false
        }
        showText()
    }

    private fun checkQuery(query: String) {
        if (query.isEmpty()) {
            searchByNameViewModel.drinks.value = emptyList()
            showText()
        } else
            searchByNameViewModel.getSearchByNameList(query)
    }

    private fun showText() {
        if (searchView.query.isNullOrEmpty()) {
            tvMessage.apply {
                visibility = View.VISIBLE
                text = getString(R.string.find_drinks)
            }
        } else tvMessage.visibility = View.GONE
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
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = arguments?.getString("title")
    }

}

