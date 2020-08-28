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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            },
            SPLASH_SHOW_TIME
        )
    }


    companion object {
        private const val SPLASH_SHOW_TIME = 400L
    }

}
