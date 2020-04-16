package com.mvasilova.cocktailrecipes.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }, SPLASH_SHOW_TIME)
    }

    companion object {
        private const val SPLASH_SHOW_TIME = 400L
    }

}
