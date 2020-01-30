package com.vbta.currenciesta.presentation.screen.base

sealed class DataState<out T> {

    object Loading : DataState<Nothing>()

    data class Loaded<T>(val data: T) : DataState<T>()

    data class Failed(val error: Throwable) : DataState<Nothing>()

}