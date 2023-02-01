package com.mvasilova.cocktailrecipes.app.ext

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.app.view.DividerItemDecoration
import com.mvasilova.cocktailrecipes.app.view.LinkTouchMovementMethod
import com.mvasilova.cocktailrecipes.app.view.TouchableSpan
import com.mvasilova.cocktailrecipes.presentation.delegates.AlphabetLetter

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun GridLayoutManager.setCustomSpanSizeLookup(
    adapter: ListDelegationAdapter<List<DisplayableItem>>,
    modelCount: Int,
    defaultCount: Int
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
            context.getCompatColor(R.color.colorView), 1.dpToPx, marginStart, marginEnd
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

fun TextView.setOnEnterClickListener(action: (TextView) -> Unit) {
    setOnEditorActionListener { textView, actionId, keyEvent ->
        if (keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
            action(textView)
        }
        false
    }
}

inline fun TextView.setupLink(
    text: String,
    link: String,
    normalColor: Int,
    pressedColor: Int,
    crossinline func: () -> Unit
) {

    if (!text.contains(link) || text.isEmpty() || link.isEmpty()) {
        this.text = text
        return
    }

    val ssb = SpannableStringBuilder(text)

    val startIndex = text.indexOf(link)
    val lastIndex = startIndex + link.length

    ssb.setSpan(
        object : TouchableSpan(normalColor, pressedColor) {
            override fun onClick(view: View) = func.invoke()
        },
        startIndex, lastIndex, 0
    )

    this.movementMethod = LinkTouchMovementMethod()
    this.text = ssb
}

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.setStartDrawable(@DrawableRes drawableId: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableId, 0, 0, 0)
}