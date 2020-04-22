package com.mvasilova.cocktailrecipes.presentation.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.di.module.GlideApp
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setData
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.presentation.delegates.RecipeInfo
import com.mvasilova.cocktailrecipes.presentation.delegates.RecipeTitle
import com.mvasilova.cocktailrecipes.presentation.delegates.itemRecipeInfo
import com.mvasilova.cocktailrecipes.presentation.delegates.itemRecipeTitle
import kotlinx.android.synthetic.main.fragment_recipe_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RecipeInfoFragment : BaseFragment(R.layout.fragment_recipe_info) {

    override val screenViewModel by viewModel<RecipeInfoViewModel>() {
        parametersOf(args.idDrink)
    }
    private val args: RecipeInfoFragmentArgs by navArgs()

    val recipeAdapter by lazy {
        ListDelegationAdapter(
            itemRecipeTitle(),
            itemRecipeInfo()
        )
    }

    private lateinit var text: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(screenViewModel.recipe, ::handleRecipeInfo)
        observe(screenViewModel.isFavorite, ::handleIsFavorite)
        setupToolbar()
        setupRecipe()
    }

    private fun handleIsFavorite(isFavorite: Boolean?) {
        ivFavorite.setImageResource(
            if (isFavorite == true) R.drawable.ic_favorite_fill
            else R.drawable.ic_favorite_border
        )
    }

    private fun handleRecipeInfo(recipe: RecipeInfoDrink?) {
        recipe?.let {
            val drink = recipe.drinks?.get(0)

            collapsingToolbar.title = drink?.strDrink
            GlideApp.with(this).load(drink?.strDrinkThumb)
                .optionalCenterCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivDrink)

            val newList = mutableListOf<String>()
            drink?.ingredients?.forEachIndexed { index, s ->
                val measure =
                    if (drink.measure.getOrNull(index) != null) drink.measure[index] else ""
                newList.add("$measure $s".trimStart())
            }

            val ingredients = newList.mapIndexed { index, s -> Pair(index, s) }
                .joinToString(
                    separator = "\n",
                    transform = { "${resources.getString(R.string.dot)} ${it.second}" })

            val itemRecipe = listOf(
                RecipeTitle(getString(R.string.filter_ingredients)),
                RecipeInfo(ingredients),
                RecipeTitle(getString(R.string.instructions)),
                RecipeInfo(drink?.strInstructions.orEmpty()),
                RecipeTitle(getString(R.string.filter_glass)),
                RecipeInfo(drink?.strGlass.orEmpty())
            )

            recipeAdapter.setData(itemRecipe)

            text =
                "${drink?.strDrink}\n${getString(R.string.filter_ingredients)}:\n$ingredients\n\n" +
                        "${getString(R.string.instructions)}:\n${drink?.strInstructions}\n\n${getString(
                            R.string.filter_glass
                        )}:\n${drink?.strGlass}" +
                        "\n\n${getString(R.string.drinks_image)}:\n${drink?.strDrinkThumb}" +
                        "\n\n${resources.getString(R.string.intent_text)} "
        }
    }

    private fun setupRecipe() {
        rvRecipe.layoutManager = LinearLayoutManager(context)
        rvRecipe.adapter = recipeAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = ""

        ivFavorite.setOnClickListener {
            screenViewModel.changeFavorite()
        }

        ivShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
        }
    }

}