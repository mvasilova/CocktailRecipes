package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import kotlinx.android.synthetic.main.item_alphabet_letter.view.*

fun itemAlphabet() =
    adapterDelegateLayoutContainer<AlphabetLetter, DisplayableItem>(R.layout.item_alphabet_letter) {

        bind {
            with(containerView) {
                tvAlphabetLetter.text = item.letter
            }
        }
    }

data class AlphabetLetter(val letter: String) : DisplayableItem {
    override val itemId: String
        get() = letter
}