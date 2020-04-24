package com.mvasilova.cocktailrecipes.presentation.filter.filterbyparameters

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.*
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.view.AlphabetItemDecoration
import com.mvasilova.cocktailrecipes.data.entity.Filter
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters
import com.mvasilova.cocktailrecipes.data.enums.TypeDrinksFilters.INGREDIENTS
import com.mvasilova.cocktailrecipes.presentation.delegates.itemFilterName
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_toolbar_search_view.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilterByParametersFragment : BaseFragment(R.layout.fragment_list) {

    override val toolbarTitle: String
        get() = when (args.type) {
            TypeDrinksFilters.ALCOHOL -> getString(R.string.filter_by_alcohol)
            TypeDrinksFilters.CATEGORY -> getString(R.string.filter_by_category)
            TypeDrinksFilters.GLASS -> getString(R.string.filter_by_glass)
            INGREDIENTS -> {
                getString(R.string.multi_filter_by_ingredients)
            }
        }

    override val setToolbar: Boolean
        get() = true

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
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

    private fun handleFilters(filters: List<Filter>?) {
        filters?.let { list ->
            tvMessage.text = getString(R.string.not_found)
            tvMessage.isVisible = list.isNullOrEmpty()
            filtersAdapter.setData(list)
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDrinks.adapter = filtersAdapter
        rvDrinks.setPadding(0.dpToPx)

        if (args.type == INGREDIENTS) {
            btnSearch.visibility = View.VISIBLE
            rvDrinks.setDividerItemDecoration(50f.dpToPx)
            rvDrinks.addItemDecoration(
                AlphabetItemDecoration(
                    requireActivity(),
                    resources.getDimensionPixelSize(R.dimen.search_decoration_padding_26),
                    getGroupId = { position ->
                        when (val item = filtersAdapter.items[position]) {
                            is Filter -> item.name.first().toUpperCase().toLong()
                            else -> AlphabetItemDecoration.EMPTY_ID
                        }
                    },
                    getInitial = { position ->
                        when (val item = filtersAdapter.items[position]) {
                            is Filter -> item.name.first().toUpperCase().toString()
                            else -> AlphabetItemDecoration.DEFAULT_INITIAL
                        }
                    })
            )
        } else {
            rvDrinks.setDividerItemDecoration()
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

    private fun setupSearchView() {
        searchView.setOnTextChangeListener {
            screenViewModel.filterBySearch(it)
        }
    }
}

