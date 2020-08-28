package com.mvasilova.cocktailrecipes.app.ext

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import androidx.annotation.ColorInt

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

fun Activity.setStatusBarLightMode(
    isLightMode: Boolean
) {
    val decorView = this.window.decorView
    var vis = decorView.systemUiVisibility
    vis = if (isLightMode) {
        vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    decorView.systemUiVisibility = vis
}


fun Activity.isUsingNightModeResources(): Boolean {
    return when (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }
}