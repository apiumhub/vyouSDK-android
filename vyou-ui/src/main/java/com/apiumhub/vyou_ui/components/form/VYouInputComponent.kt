package com.apiumhub.vyou_ui.components.form

import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException

interface VYouInputComponent {
    val id: String
    var visible: Boolean
    fun setValue(value: String)
    fun getKeyValue(): FieldOutModel?

    @Throws(ValidationException::class)
    fun validate(): VYouInputComponent
}
