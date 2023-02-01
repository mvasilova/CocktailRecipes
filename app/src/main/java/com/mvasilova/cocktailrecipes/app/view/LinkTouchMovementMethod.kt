package com.mvasilova.cocktailrecipes.app.view

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

class LinkTouchMovementMethod : LinkMovementMethod() {
    private var pressedSpan: TouchableSpan? = null

    override fun onTouchEvent(
        textView: TextView,
        spannable: Spannable,
        event: MotionEvent
    ): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            pressedSpan = getPressedSpan(textView, spannable, event)
            if (pressedSpan != null) {
                pressedSpan!!.setPressed(true)
                Selection.setSelection(
                    spannable,
                    spannable.getSpanStart(pressedSpan),
                    spannable.getSpanEnd(pressedSpan)
                )
            }
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val touchedSpan = getPressedSpan(textView, spannable, event)
            if (pressedSpan != null && touchedSpan !== pressedSpan) {
                pressedSpan!!.setPressed(false)
                pressedSpan = null
                Selection.removeSelection(spannable)
            }
        } else {
            if (pressedSpan != null) {
                pressedSpan!!.setPressed(false)
                super.onTouchEvent(textView, spannable, event)
            }
            pressedSpan = null
            Selection.removeSelection(spannable)
        }
        return true
    }

    private fun getPressedSpan(
        textView: TextView,
        spannable: Spannable,
        event: MotionEvent
    ): TouchableSpan? {

        val x = event.x.toInt() - textView.totalPaddingLeft + textView.scrollX
        val y = event.y.toInt() - textView.totalPaddingTop + textView.scrollY

        val layout = textView.layout
        val position = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x.toFloat())

        val link = spannable.getSpans(position, position, TouchableSpan::class.java)
        var touchedSpan: TouchableSpan? = null
        if (link.isNotEmpty() && positionWithinTag(position, spannable, link[0])) {
            touchedSpan = link[0]
        }

        return touchedSpan
    }

    private fun positionWithinTag(position: Int, spannable: Spannable, tag: Any): Boolean {
        return position >= spannable.getSpanStart(tag) && position <= spannable.getSpanEnd(tag)
    }
}
