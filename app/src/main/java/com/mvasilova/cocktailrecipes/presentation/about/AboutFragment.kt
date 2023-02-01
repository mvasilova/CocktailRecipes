package com.mvasilova.cocktailrecipes.presentation.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.mvasilova.cocktailrecipes.BuildConfig
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.applyTheme
import com.mvasilova.cocktailrecipes.app.ext.setupLink
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import com.mvasilova.cocktailrecipes.app.view.ChooseThemeDialog
import com.mvasilova.cocktailrecipes.app.view.ChooseThemeDialogListener
import com.mvasilova.cocktailrecipes.app.view.DialogButton
import com.mvasilova.cocktailrecipes.app.view.DialogEvent
import com.mvasilova.cocktailrecipes.data.enums.AppTheme
import com.mvasilova.cocktailrecipes.data.storage.Pref
import kotlinx.android.synthetic.main.fragment_about.*
import org.koin.android.ext.android.inject

class AboutFragment : BaseFragment(R.layout.fragment_about), ChooseThemeDialogListener {

    private val pref: Pref by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {

        tvVersionName.text = getString(R.string.placeholder_version_name, BuildConfig.VERSION_NAME)

        val normalColor = ContextCompat.getColor(requireContext(), R.color.colorPaleText)
        val pressedColor = ContextCompat.getColor(requireContext(), R.color.colorLightHint)

        tvSpecialThanks.setupLink(
            resources.getString(R.string.label_special_thanks),
            resources.getString(R.string.label_special_thanks_link),
            normalColor,
            pressedColor
        ) {
            openUrl(getString(R.string.url_cocktail_db))
        }

        ivTelegram.setOnClickListener {
            openUrl(getString(R.string.url_telegram))
        }

        ivInstagram.setOnClickListener {
            openUrl(getString(R.string.url_instagram))
        }

        btnChooseTheme.setOnClickListener {
            openChooseThemeDialog()
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun openChooseThemeDialog() {
        if (dialogNotAlreadyShown(ChooseThemeDialog.TAG)) {
            ChooseThemeDialog.Builder()
                .tag(DIALOG_CHOOSE_THEME)
                .payload(pref.appTheme ?: AppTheme.SYSTEM)
                .build()
                .apply {
                    setTargetFragment(this@AboutFragment, ChooseThemeDialog.REQUEST_CODE)
                }
                .show(parentFragmentManager, ChooseThemeDialog.TAG)
        }
    }

    override fun onChooseThemeDialogEvent(event: DialogEvent) {
        when (event.tag) {
            DIALOG_CHOOSE_THEME -> {
                if (event.button == DialogButton.OK) {
                    pref.appTheme = event.payload as? AppTheme
                    pref.appTheme?.applyTheme()
                }
            }
        }
    }

    companion object {
        private const val DIALOG_CHOOSE_THEME = "DialogChooseTheme"
    }
}
