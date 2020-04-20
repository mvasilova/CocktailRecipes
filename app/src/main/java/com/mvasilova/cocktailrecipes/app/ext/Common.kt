package com.mvasilova.cocktailrecipes.app.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}