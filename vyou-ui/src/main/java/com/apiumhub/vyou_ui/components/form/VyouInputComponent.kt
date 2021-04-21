package com.apiumhub.vyou_ui.components.form

import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException

interface VyouInputComponent {
    val id: String
    var visible: Boolean
    fun setValue(value: String)
    fun getKeyValue(): FieldOutModel?
    @Throws(ValidationException::class)
    fun validate(): VyouInputComponent
}
