package com.apiumhub.vyou_core.domain

class VyouException : Exception {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}