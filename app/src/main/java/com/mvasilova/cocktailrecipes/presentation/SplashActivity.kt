package com.mvasilova.cocktailrecipes.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mvasilova.cocktailrecipes.app.ext.isUsingNightModeResources
import com.mvasilova.cocktailrecipes.data.enums.AppTheme
import com.mvasilova.cocktailrecipes.data.storage.Pref
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val runnable = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(runnable, SPLASH_SHOW_TIME)
    }

    override fun onPause() {
        super.onPause()
        Handler(Looper.getMainLooper()).removeCallbacks(runnable)
    }


    companion object {
        private const val SPLASH_SHOW_TIME = 400L
    }

}
