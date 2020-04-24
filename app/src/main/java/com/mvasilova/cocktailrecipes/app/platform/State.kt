package com.mvasilova.cocktailrecipes.app.platform

sealed class State {
    object Loading : State()
    object Loaded : State()
    data class Error(val throwable: Throwable) : State()
}