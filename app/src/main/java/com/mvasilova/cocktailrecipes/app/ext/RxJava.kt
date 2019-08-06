package com.mvasilova.cocktailrecipes.app.ext

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.observeMainThread() =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.observeMainThread() =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())