package com.mvasilova.cocktailrecipes.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.presentation.delegates.homeHorizontalPreviewDelegate
import com.mvasilova.cocktailrecipes.presentation.delegates.homePreviewCategoryDelegate
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val screenViewModel by viewModel<HomeViewModel>()

    val homeHorizontalPreviewAdapter by lazy {
        ListDelegationAdapter(
            homeHorizontalPreviewDelegate() {
                val action = HomeDirections.actionGlobalRecipeInfoFragment(it)
                findNavController().navigate(action)
            },
            homePreviewCategoryDelegate({
                val action = HomeDirections.actionGlobalRecipeInfoFragment(it)
                findNavController().navigate(action)
            }, {
                val bundle = Bundle()
                bundle.putSerializable(PREVIEW_CATEGORY_KEY, it)
                findNavController().navigate(
                    R.id.action_global_drinksListFragment, bundle
                )
            })
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupDrinks()
        setupObservers()
    }

    private fun setupObservers() {
        observe(screenViewModel.drinks, ::handleDrinks)
    }

    private fun handleDrinks(drinks: List<Any>?) {
        drinks?.let {
            homeHorizontalPreviewAdapter.setData(drinks)
        }
    }

    private fun setupDrinks() {
        rvHome.layoutManager = LinearLayoutManager(context)
        rvHome.adapter = homeHorizontalPreviewAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        ivSearch.setOnClickListener {
            val action = HomeDirections.actionGlobalSearchByNameFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        const val PREVIEW_CATEGORY_KEY = "PreviewCategory"
    }
}