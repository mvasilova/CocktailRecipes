package com.mvasilova.cocktailrecipes.app.view

import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class TouchableSpan(
    private val normalTextColor: Int,
    private val pressedTextColor: Int,
    private val isUnderLine: Boolean = true
) : ClickableSpan() {

    private var isPressed: Boolean = false

    open fun setPressed(isSelected: Boolean) {
        isPressed = isSelected
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = if (isPressed) pressedTextColor else normalTextColor
        ds.isUnderlineText = isUnderLine
    }
}