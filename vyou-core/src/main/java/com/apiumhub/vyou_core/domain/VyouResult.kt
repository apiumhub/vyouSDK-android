package com.apiumhub.vyou_core.domain

import android.util.Log

sealed class VyouResult<T> {
    class Success<T>(val value: T): VyouResult<T>()
    class Failure<T>(val error: Throwable): VyouResult<T>() {
        init{
            Log.e("Error", "Error inside VYou", error)
        }
    }
}