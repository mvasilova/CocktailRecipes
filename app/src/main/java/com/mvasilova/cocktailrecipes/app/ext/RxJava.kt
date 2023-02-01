package com.mvasilova.cocktailrecipes.app.ext

import androidx.lifecycle.MutableLiveData
import com.mvasilova.cocktailrecipes.app.platform.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T : Any> Single<T>.observeMainThread(): Single<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.observeMainThread(): Observable<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.observeMainThread(): Completable =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.handleState(state: MutableLiveData<State>): Single<T> =
    doOnSubscribe { state.value = State.Loading }
        .doOnError { state.value = State.Error(it) }
        .doAfterSuccess { state.value = State.Loaded }

fun <T : Any> Observable<T>.handleState(state: MutableLiveData<State>): Observable<T> =
    doOnSubscribe { state.value = State.Loading }
        .doOnError { state.value = State.Error(it) }
        .doOnComplete { state.value = State.Loaded }

fun Completable.handleError(state: MutableLiveData<State>): Completable =
    doOnError {
        state.value = State.Error(it)
    }
