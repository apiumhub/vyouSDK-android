package com.apiumhub.vyou_ui.register.ui.form

import com.apiumhub.vyou_ui.register.ui.exception.ValidationException

interface VyouInputComponent {
    fun getKeyValue(): Pair<String, String>?
    @Throws(ValidationException::class)
    fun validate(): VyouInputComponent
}
