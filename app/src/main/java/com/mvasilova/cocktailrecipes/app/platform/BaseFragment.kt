package com.mvasilova.cocktailrecipes.app.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.getCompatColor
import com.mvasilova.cocktailrecipes.app.ext.observe
import com.mvasilova.cocktailrecipes.app.ext.setStatusBarColor
import com.mvasilova.cocktailrecipes.app.ext.setStatusBarLightMode
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.layout_toolbar_search_view.*

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    open val supportFragmentManager
        get() = requireActivity().supportFragmentManager

    open val screenViewModel: BaseViewModel? = null

    open val statusBarColor = R.color.colorStatusBarBackground
    open val statusBarLightMode = false

    open val setToolbar = false
    open val setDisplayHomeAsUpEnabled = true
    open val toolbarTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatusBar(statusBarColor, statusBarLightMode)

        screenViewModel?.let {
            observe(it.state, ::handleState)
        }
        if (setToolbar) {
            setupToolbar()
        }
    }

    protected open fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar?.isVisible = true
            State.Loaded -> progressBar?.isVisible = false
            is State.Error -> Unit // todo handle error on UI
            else -> Unit
        }
    }

    protected fun setupStatusBar(statusBarColor: Int, statusBarLightMode: Boolean) {
        requireActivity().setStatusBarColor(requireActivity().getCompatColor(statusBarColor))
        requireActivity().setStatusBarLightMode(statusBarLightMode)
    }

    private fun setupToolbar() {
        toolbar?.let { (activity as AppCompatActivity).setSupportActionBar(it) }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            setDisplayHomeAsUpEnabled
        )
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = toolbarTitle
        searchView?.maxWidth = Int.MAX_VALUE
    }

    internal fun dialogNotAlreadyShown(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) == null
}
