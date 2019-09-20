package com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.app.ui.home.adapters.PreviewDrinksCategoryAdapter
import com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks.CategoriesPreviewDrinks.*
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_preview_cat.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PreviewDrinksFragment : Fragment(R.layout.fragment_list_preview_cat) {

    private lateinit var bundle: Bundle
    private val category by lazy { arguments?.getSerializable(ARGUMENT_CATEGORY) }
    private val previewDrinksViewModel: PreviewDrinksViewModel by viewModel {
        parametersOf(
            category
        )
    }
    private val previewDrinksCategoryAdapter by lazy { PreviewDrinksCategoryAdapter(::onRecipeInfoFragment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTitle()
        setupRecyclerView()
        observe(previewDrinksViewModel.state, ::handleState)
        observe(previewDrinksViewModel.drinks, ::handleDrinks)


        buttonOpenList.setOnClickListener {
            if (::bundle.isInitialized) {
                findNavController().navigate(R.id.action_global_drinksListFragment, bundle)
            }
        }

    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleDrinks(drinks: DrinksFilter?) {
        previewDrinksCategoryAdapter.collection = drinks!!.drinks.takeLast(6)
        bundle = bundleOf("list" to drinks.drinks, "title" to tvNameCategory.text)
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)

        }
    }

    private fun setupTitle() {
        when (category) {
            COCKTAILS -> tvNameCategory.text = getString(R.string.title_cocktails)
            SHOTS -> tvNameCategory.text = getString(R.string.title_shots)
            BEERS -> tvNameCategory.text = getString(R.string.title_beers)
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 3)
        rvDrinks.adapter = previewDrinksCategoryAdapter
    }

    companion object {
        const val ARGUMENT_CATEGORY = "category"

        fun newInstance(category: CategoriesPreviewDrinks) =
            PreviewDrinksFragment().apply {
                arguments = bundleOf(ARGUMENT_CATEGORY to category)
            }
    }

}

enum class CategoriesPreviewDrinks { COCKTAILS, SHOTS, BEERS }