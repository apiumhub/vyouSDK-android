package com.apiumhub.vyou_ui.register.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.apiumhub.vyou_ui.register.domain.DateField
import com.apiumhub.vyou_ui.register.domain.InputField
import com.apiumhub.vyou_ui.register.domain.RadioGroupField
import com.apiumhub.vyou_ui.register.domain.TextField
import com.apiumhub.vyou_ui.register.ui.components.DateInputView
import com.apiumhub.vyou_ui.register.ui.components.RadioGroupInputView
import com.apiumhub.vyou_ui.register.ui.components.TextInputView
import com.apiumhub.vyou_ui.register.ui.components.VyouInputComponent

internal class DynamicFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun render(fields: List<InputField>) {
        fields
            .map { createComponent(it) }
            .forEach { addView(it) }
    }

    fun getResponses(): List<Pair<String, String>> =
        children
            .map { (it as VyouInputComponent).getKeyValue() }
            .toList()

    private fun createComponent(it: InputField): View =
        when (it) {
            is DateField -> DateInputView(context, it)
            is RadioGroupField -> RadioGroupInputView(context, it)
            is TextField -> TextInputView(context, it)
        }
}