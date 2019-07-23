package com.mvasilova.cocktailrecipes.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ui.cocktailslist.CocktailsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        openFragment(CocktailsFragment())
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, null)
            .addToBackStack(null)
            .commit()
    }
}
