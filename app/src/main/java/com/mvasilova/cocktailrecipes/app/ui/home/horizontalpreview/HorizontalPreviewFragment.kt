package com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview

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
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks.POPULAR
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks.RECENT
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import kotlinx.android.synthetic.main.fragment_list_preview_horizontal.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HorizontalPreviewFragment : Fragment(R.layout.fragment_list_preview_horizontal) {

    private val type by lazy { arguments?.getSerializable(ARGUMENT_TYPE) }
    private val horizontalPreviewViewModel: HorizontalPreviewViewModel by viewModel {
        parametersOf(
            type
        )
    }
    private val previewDrinksCategoryAdapter by lazy {
        PreviewDrinksCategoryAdapter(::onRecipeInfoFragment).apply {
            titleIsVisible = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (type) {
            RECENT -> tvNameCategory.text = getString(R.string.title_recent)
            POPULAR -> tvNameCategory.text = getString(R.string.title_popular)
        }

        setupRecyclerView()
        observe(horizontalPreviewViewModel.state, ::handleState)
        observe(horizontalPreviewViewModel.drinks, ::handleRecentDrinks)

    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleRecentDrinks(drinks: DrinksFilter?) {
        previewDrinksCategoryAdapter.collection = drinks!!.drinks
    }

    private fun onRecipeInfoFragment(id: String?) {
        if (id != null) {
            val action = HomeDirections.actionGlobalRecipeInfoFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        rvDrinks.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        rvDrinks.adapter = previewDrinksCategoryAdapter
        indicator.attachToRecyclerView(rvDrinks)
    }

    companion object {
        const val ARGUMENT_TYPE = "type"

        fun newInstance(type: TypePreviewDrinks) =
            HorizontalPreviewFragment().apply {
                arguments = bundleOf(ARGUMENT_TYPE to type)
            }
    }
}

enum class TypePreviewDrinks { RECENT, POPULAR }