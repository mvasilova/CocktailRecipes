package com.mvasilova.cocktailrecipes.app.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()
    val state: MutableLiveData<State> = MutableLiveData()

    protected fun Disposable.addToDisposables() = disposables.add(this)

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}