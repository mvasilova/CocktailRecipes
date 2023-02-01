package com.mvasilova.cocktailrecipes.app.ext

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.isUsingNightModeResources(): Boolean {
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}
