package com.mvasilova.cocktailrecipes.app.ui.recipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.platform.State
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import kotlinx.android.synthetic.main.fragment_recipe_info.*
import org.jetbrains.anko.support.v4.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RecipeInfoFragment : Fragment(R.layout.fragment_recipe_info) {

    private val args: RecipeInfoFragmentArgs by navArgs()
    private val viewModel: RecipeInfoViewModel by viewModel { parametersOf(args.idDrink) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.state, ::handleState)
        observe(viewModel.recipe, ::handleRecipeInfo)
        observe(viewModel.isFavorite, ::handleIsFavorite)
        setupToolbar()
    }

    private fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar.visibility = View.VISIBLE
            State.Loaded -> progressBar.visibility = View.GONE
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    private fun handleIsFavorite(isFavorite: Boolean?) {
        if (isFavorite == true) ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
        else ivFavorite.setImageResource(R.drawable.ic_favorite_border)
    }

    private fun handleRecipeInfo(recipe: RecipeInfoDrink?) {
        val drink = recipe!!.drinks!![0]
        collapsing_toolbar.title = drink.strDrink
        GlideApp.with(this).load(drink.strDrinkThumb)
            .optionalCenterCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivDrink)
        val newList = mutableListOf<String>()
        drink.ingredients.forEachIndexed { index, s ->
            val measure = if (drink.measure.getOrNull(index) != null) drink.measure.get(index) else ""
            newList.add("$measure $s".trimStart())
        }
        tvIngredients.text = newList.mapIndexed { index, s -> Pair(index, s) }
            .joinToString(separator = "\n", transform = { "${it.first + 1}) ${it.second}" })
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = ""

        ivFavorite.setOnClickListener {
            viewModel.changeFavorite()
        }
    }


}