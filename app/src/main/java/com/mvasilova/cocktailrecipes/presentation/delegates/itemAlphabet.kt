package com.mvasilova.cocktailrecipes.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem
import com.mvasilova.cocktailrecipes.databinding.ItemAlphabetLetterBinding

fun itemAlphabet() =
    adapterDelegateViewBinding<AlphabetLetter, DisplayableItem, ItemAlphabetLetterBinding>(
        { layoutInflater, root ->
            ItemAlphabetLetterBinding.inflate(layoutInflater, root, false)
        }
    ) {

        bind {
            binding.apply {
                tvAlphabetLetter.text = item.letter
            }
        }
    }

data class AlphabetLetter(val letter: String) : DisplayableItem {
    override val itemId: String
        get() = letter
}
