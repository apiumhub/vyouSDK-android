package com.apiumhub.vyou_core.domain

class VYouException : Exception {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}