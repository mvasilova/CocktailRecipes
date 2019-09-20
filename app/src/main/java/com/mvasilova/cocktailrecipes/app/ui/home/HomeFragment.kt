package com.mvasilova.cocktailrecipes.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvasilova.cocktailrecipes.HomeDirections
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.HorizontalPreviewFragment
import com.mvasilova.cocktailrecipes.app.ui.home.horizontalpreview.TypePreviewDrinks
import com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks.CategoriesPreviewDrinks
import com.mvasilova.cocktailrecipes.app.ui.home.previewdrinks.PreviewDrinksFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        setupPreviewDrinksFragment(
            HorizontalPreviewFragment.newInstance(TypePreviewDrinks.POPULAR),
            R.id.horizontalPopularDrinks
        )

        setupPreviewDrinksFragment(
            HorizontalPreviewFragment.newInstance(TypePreviewDrinks.RECENT),
            R.id.horizontalRecentDrinks
        )

        setupPreviewDrinksFragment(
            PreviewDrinksFragment.newInstance(CategoriesPreviewDrinks.COCKTAILS),
            R.id.previewCocktails
        )

        setupPreviewDrinksFragment(
            PreviewDrinksFragment.newInstance(CategoriesPreviewDrinks.SHOTS),
            R.id.previewShots
        )

        setupPreviewDrinksFragment(
            PreviewDrinksFragment.newInstance(CategoriesPreviewDrinks.BEERS),
            R.id.previewBeers
        )
    }


    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        ivSearch.setOnClickListener {
            val action = HomeDirections.actionGlobalSearchByNameFragment()
            findNavController().navigate(action)
        }
    }

    fun setupPreviewDrinksFragment(fragment: Fragment, resId: Int) {
        if (childFragmentManager.findFragmentById(resId) == null) {
            childFragmentManager
                .beginTransaction()
                .replace(resId, fragment, null)
                .commit()
        }
    }
}