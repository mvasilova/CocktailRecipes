package com.mvasilova.cocktailrecipes.presentation.favorites

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
import com.mvasilova.cocktailrecipes.FavoritesDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.presentation.MainActivity
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment(R.layout.fragment_list) {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val favoritesAdapter by lazy { FavoritesAdapter(::onRecipeInfoFragment) }
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

        observe(favoritesViewModel.favorites, ::handleFavorites)
        observe(favoritesViewModel.mediator, {})

    }

    override fun onPause() {
        super.onPause()
        query = searchView.query.toString()
    }

    private fun handleFavorites(favorite: List<Favorite>?) {
        if (favorite.isNullOrEmpty()) {
            tvMessage.text = getString(R.string.not_found)
            tvMessage.visibility = View.VISIBLE
            favoritesAdapter.collection = favorite ?: listOf()

        } else {
            tvMessage.visibility = View.GONE
            favoritesAdapter.collection = favorite
        }
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
                favoritesViewModel.filterBySearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                favoritesViewModel.filterBySearch(newText)

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

