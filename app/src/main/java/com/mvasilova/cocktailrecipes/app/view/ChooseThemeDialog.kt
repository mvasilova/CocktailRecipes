package com.mvasilova.cocktailrecipes.app.view

import android.app.Dialog
import android.os.Bundle
import java.io.Serializable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.data.enums.AppTheme
import kotlinx.android.synthetic.main.dialog_choose_theme.view.*

class ChooseThemeDialog : DialogFragment() {

    private var listener: ChooseThemeDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listener =
            (targetFragment as? ChooseThemeDialogListener) ?: activity as? ChooseThemeDialogListener

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_theme, null)
        val builder = AlertDialog.Builder(requireContext(), R.style.MaterialAlertDialog_Theme)

        isCancelable = true

        builder.setView(view)
        setupButtons(view, builder)

        return builder.create()
    }

    private fun setupButtons(view: View, builder: AlertDialog.Builder) {
        val tag = arguments?.getString(TAG_KEY)
        val payload: Serializable? = arguments?.getSerializable(PAYLOAD_KEY)

        val positiveButton = R.string.btn_ok
        val negativeButton = R.string.btn_cancel


        builder.setPositiveButton(positiveButton) { _, _ -> positiveAction(view, tag) }

        builder.setNegativeButton(negativeButton) { _, _ ->
            negativeAction(tag)
        }

        when (payload as? AppTheme) {
            AppTheme.LIGHT -> view.rgChooseTheme.check(R.id.rbThemeLight)
            AppTheme.DARK -> view.rgChooseTheme.check(R.id.rbThemeDark)
            AppTheme.SYSTEM -> view.rgChooseTheme.check(R.id.rbThemeSystem)
            null -> view.rgChooseTheme.check(R.id.rbThemeSystem)
        }
    }

    private fun positiveAction(view: View, tag: String?) {
        val payload: Serializable? = when (view.rgChooseTheme?.checkedRadioButtonId) {
            R.id.rbThemeLight -> AppTheme.LIGHT
            R.id.rbThemeDark -> AppTheme.DARK
            R.id.rbThemeSystem -> AppTheme.SYSTEM
            else -> AppTheme.SYSTEM
        }

        listener?.onChooseThemeDialogEvent(DialogEvent(DialogButton.OK, tag, payload))
        dismiss()
    }

    private fun negativeAction(tag: String?) {
        listener?.onChooseThemeDialogEvent(
            DialogEvent(
                DialogButton.CANCEL,
                tag
            )
        )
        dismiss()
    }

    class Builder {
        private var tag: String? = null
        private var payload: Serializable? = null

        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun payload(payload: Serializable): Builder {
            this.payload = payload
            return this
        }

        fun build(): ChooseThemeDialog {
            val instance = ChooseThemeDialog()
            val args = Bundle()
            args.putString(TAG_KEY, tag)
            args.putSerializable(PAYLOAD_KEY, payload)
            instance.arguments = args
            return instance
        }

    }

    companion object {

        const val TAG = "CommonDialog"
        const val REQUEST_CODE = 111
        const val TAG_KEY = "TagKey"
        const val PAYLOAD_KEY = "PayloadKey"
    }

}


class DialogEvent(
    val button: DialogButton,
    val tag: String?,
    val payload: Serializable? = null
)

enum class DialogButton {
    OK,
    CANCEL
}

interface ChooseThemeDialogListener {
    fun onChooseThemeDialogEvent(event: DialogEvent)
}