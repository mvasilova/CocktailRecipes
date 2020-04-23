package com.mvasilova.cocktailrecipes.app.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.app.view.DividerItemDecoration
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

fun RecyclerView.setDividerItemDecoration(
    marginStart: Float = 10f.dpToPx,
    marginEnd: Float = 10f.dpToPx
) {
    addItemDecoration(
        DividerItemDecoration(
            ContextCompat.getColor(
                context,
                R.color.colorView
            ), 1.dpToPx, marginStart, marginEnd
        )
    )
}

fun SearchView.setOnTextChangeListener(onTextChanged: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onTextChanged.invoke(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            onTextChanged.invoke(newText)
            return false
        }
    })
}