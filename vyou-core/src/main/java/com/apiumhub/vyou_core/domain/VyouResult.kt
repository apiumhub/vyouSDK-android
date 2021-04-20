package com.apiumhub.vyou_core.domain

sealed class VyouResult<T> {
    class Success<T>(val value: T): VyouResult<T>()
    class Failure<T>(val error: Throwable): VyouResult<T>()
}