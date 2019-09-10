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
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupPopularDrinksFragment()
        setupRecentDrinksFragment()
    }


    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        ivSearch.setOnClickListener {
            val action = HomeDirections.actionGlobalSearchByNameFragment()
            findNavController().navigate(action)
        }
    }

    fun setupPopularDrinksFragment() {
        val fragment = HorizontalPreviewFragment.newInstance(TypePreviewDrinks.POPULAR)
        if (childFragmentManager.findFragmentById(R.id.horizontalPopularDrinks) == null) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.horizontalPopularDrinks, fragment, null)
                .addToBackStack(null)
                .commit()
        }
    }

    fun setupRecentDrinksFragment() {
        val fragment = HorizontalPreviewFragment.newInstance(TypePreviewDrinks.RECENT)
        if (childFragmentManager.findFragmentById(R.id.horizontalRecentDrinks) == null) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.horizontalRecentDrinks, fragment, null)
                .addToBackStack(null)
                .commit()
        }
    }

}