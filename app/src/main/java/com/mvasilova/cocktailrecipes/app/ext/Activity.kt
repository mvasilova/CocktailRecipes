package com.mvasilova.cocktailrecipes.app.ext

import android.app.Activity
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

fun Activity.setStatusBarLightMode(isLightMode: Boolean) {
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLightMode
}
