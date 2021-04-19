package com.apiumhub.vyou_ui.register.ui.form

import com.apiumhub.vyou_ui.register.ui.FieldOutModel
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException

interface VyouInputComponent {
    fun getKeyValue(): FieldOutModel?
    @Throws(ValidationException::class)
    fun validate(): VyouInputComponent
}
