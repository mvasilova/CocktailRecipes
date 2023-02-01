package com.mvasilova.cocktailrecipes.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mvasilova.cocktailrecipes.app.ext.observeMainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

class StartActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.timer(SPLASH_SHOW_TIME, TimeUnit.MILLISECONDS)
            .observeMainThread()
            .doOnComplete { startApp() }
            .subscribe({}) { Log.d("ErrorInfo", "$javaClass ${it.message}") }
            .addTo(compositeDisposable)
        // todo log errors to Crashlytics
    }

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        private const val SPLASH_SHOW_TIME = 400L
    }
}
