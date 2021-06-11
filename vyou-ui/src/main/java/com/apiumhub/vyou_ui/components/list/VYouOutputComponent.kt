package com.apiumhub.vyou_ui.components.list

interface VYouOutputComponent {
    val id: String
    var visible: Boolean
    fun setValue(value: String)
}
