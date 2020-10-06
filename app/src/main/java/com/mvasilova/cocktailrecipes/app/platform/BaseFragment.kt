package com.mvasilova.cocktailrecipes.app.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import org.jetbrains.anko.support.v4.longToast

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
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

    protected fun setupStatusBar(statusBarColor: Int, statusBarLightMode: Boolean) {
        activity?.setStatusBarColor(requireActivity().getCompatColor(statusBarColor))
        activity?.setStatusBarLightMode(statusBarLightMode)
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

    fun showSoftKeyboard(field: View?) {
        field?.let {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard() {
        view?.let {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    internal fun dialogNotAlreadyShown(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) == null
}