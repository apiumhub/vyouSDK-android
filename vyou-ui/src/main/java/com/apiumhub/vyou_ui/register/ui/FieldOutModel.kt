package com.apiumhub.vyou_ui.register.ui

data class FieldOutModel (
    val fieldType: FieldType,
    val key: String,
    val value: String
)

enum class FieldType {
    EMAIL,
    PASSWORD,
    DEFAULT,
    CUSTOM
}