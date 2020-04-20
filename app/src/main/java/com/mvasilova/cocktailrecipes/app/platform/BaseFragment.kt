package com.mvasilova.cocktailrecipes.app.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.observe
import kotlinx.android.synthetic.main.layout_progress_bar.*
import org.jetbrains.anko.support.v4.longToast

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    open val screenViewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenViewModel?.let {
            observe(it.state, ::handleState)
        }
    }

    protected fun handleState(state: State?) {
        when (state) {
            State.Loading -> progressBar?.isVisible = true
            State.Loaded -> progressBar?.isVisible = false
            is State.Error -> longToast(getString(R.string.toast_error))
        }
    }

}