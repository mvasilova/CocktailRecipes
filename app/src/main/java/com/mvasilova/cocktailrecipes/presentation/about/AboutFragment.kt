package com.mvasilova.cocktailrecipes.presentation.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.mvasilova.cocktailrecipes.BuildConfig
import com.mvasilova.cocktailrecipes.R
import com.mvasilova.cocktailrecipes.app.ext.setupLink
import com.mvasilova.cocktailrecipes.app.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment(R.layout.fragment_about) {

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

    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

}

