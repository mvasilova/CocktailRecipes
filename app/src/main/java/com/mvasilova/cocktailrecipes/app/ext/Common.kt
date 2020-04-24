package com.mvasilova.cocktailrecipes.app.ext

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dpToPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)