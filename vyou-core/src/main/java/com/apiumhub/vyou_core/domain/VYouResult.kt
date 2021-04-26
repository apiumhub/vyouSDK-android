package com.apiumhub.vyou_core.domain

import android.util.Log

sealed class VYouResult<T> {
    class Success<T>(val value: T) : VYouResult<T>()
    class Failure<T>(val error: Throwable) : VYouResult<T>() {
        init {
            Log.e("Error", "Error inside VYou", error)
        }
    }
}