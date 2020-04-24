package com.mvasilova.cocktailrecipes.presentation.searchbyname

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.ext.setOnEnterClickListener
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.view.AlphabetItemDecoration
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import com.mvasilova.cocktailrecipes.presentation.delegates.itemSearchDrinks
import com.mvasilova.cocktailrecipes.presentation.delegates.itemSearchHistory
import kotlinx.android.synthetic.main.fragment_search_by_name.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByNameFragment : BaseFragment(R.layout.fragment_search_by_name) {

    override val statusBarColor: Int
        get() = R.color.colorWhite

    override val statusBarLightMode: Boolean
        get() = true

    override val screenViewModel by viewModel<SearchByNameViewModel>()

    val drinksListAdapter by lazy {
        ListDelegationAdapter(
            itemSearchDrinks {
                screenViewModel.addSearch(edSearch.text.toString())
                resetFocus()
                val action = HomeDirections.actionGlobalRecipeInfoFragment(it)
                findNavController().navigate(action)
            },
            itemSearchHistory {
                edSearch.setText(it)
                edSearch.setSelection(it.length)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupRecyclerView()

        observe(screenViewModel.drinks, ::handleDrinks)
        observe(screenViewModel.searchHistory, ::handleSearchHistory)
    }

    override fun onResume() {
        super.onResume()
        if (edSearch.text.toString().isEmpty()) {
            setFocus()
        }
    }

    private fun handleDrinks(drinks: List<Drink>?) {
        if (edSearch.text.toString().isNotEmpty()) {
            drinks?.let { list ->
                drinksListAdapter.setData(list)
            }
        }
    }

    private fun handleSearchHistory(searchHistory: List<SearchHistory>?) {
        if (edSearch.text.toString().isEmpty()) {
            searchHistory?.let { list ->
                drinksListAdapter.setData(list.reversed())
            }
        }
    }

    private fun setupRecyclerView() {
        rvSearchDrinks.layoutManager = LinearLayoutManager(activity)
        rvSearchDrinks.adapter = drinksListAdapter
        rvSearchDrinks.addItemDecoration(
            AlphabetItemDecoration(
                requireActivity(),
                resources.getDimensionPixelSize(R.dimen.search_decoration_padding_38),
                getGroupId = { position ->
                    when (val item = drinksListAdapter.items[position]) {
                        is Drink -> item.strDrink.orEmpty().first().toUpperCase().toLong()
                        else -> AlphabetItemDecoration.EMPTY_ID
                    }
                },
                getInitial = { position ->
                    when (val item = drinksListAdapter.items[position]) {
                        is Drink -> item.strDrink.orEmpty().first().toUpperCase().toString()
                        else -> AlphabetItemDecoration.DEFAULT_INITIAL
                    }
                })
        )
    }

    private fun setupSearchView() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
            resetFocus()
        }

        edSearch.doAfterTextChanged { query ->
            if (query.toString().isEmpty()) {
                setFocus()
                handleSearchHistory(screenViewModel.searchHistory.value)
            }
            screenViewModel.getSearchByNameList(query.toString())
        }

        edSearch.setOnEnterClickListener {
            screenViewModel.addSearch(edSearch.text.toString())
            resetFocus()
        }

        ivSearchIcon.setOnClickListener {
            screenViewModel.addSearch(edSearch.text.toString())
            resetFocus()
        }

        tilSearch.setEndIconOnClickListener {
            edSearch?.setText("")
        }
    }

    private fun resetFocus() {
        edSearch?.clearFocus()
        hideSoftKeyboard()
    }

    private fun setFocus() {
        edSearch?.requestFocus()
        showSoftKeyboard(edSearch)
    }

}

