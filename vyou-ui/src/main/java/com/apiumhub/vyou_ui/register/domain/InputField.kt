package com.apiumhub.vyou_ui.register.domain

internal sealed class InputField(val id: String) {
    open fun isValid() = true
}

internal class TextField(id: String, inputType: VyouInputType, val regex: String? = null) : InputField(id) {

    enum class VyouInputType(val type: String) {
        TEXT("string"),
        NUMBER("number"),
        EMAIL("email"),
        PASSWORD("password");

        companion object {
            fun fromType(type: String) = values().find { it.type == type } ?: TEXT
        }
    }
}

internal class DateField(id: String) : InputField(id) {

}

internal class RadioGroupField(id: String, val options: List<String>) : InputField(id) {

}
