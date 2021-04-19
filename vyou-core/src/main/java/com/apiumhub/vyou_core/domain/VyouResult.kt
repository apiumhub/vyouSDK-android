package com.apiumhub.vyou_core.domain

sealed class VyouResult {
    class Success<T>(val value: T): VyouResult()
    class Error(val error: Throwable): VyouResult()
}