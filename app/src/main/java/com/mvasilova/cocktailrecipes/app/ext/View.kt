package com.mvasilova.cocktailrecipes.app.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun GridLayoutManager.setCustomSpanSizeLookup(
    adapter: ListDelegationAdapter<List<DisplayableItem>>, modelCount: Int, defaultCount: Int
) {
    spanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.items[position] is AlphabetLetter) {
                    modelCount
                } else {
                    defaultCount
                }
            }
        }
}
