package com.apiumhub.vyou_ui.components.checkboxes

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children

internal class DynamicCheckBoxesFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    val isValid get() = children.map {it as CheckBoxInputView }.all { checkBox ->
        checkBox.isRequired.not() || checkBox.isChecked
    }

    fun render(fields: List<CheckBoxField>, isTenantCompliant: Boolean = false) {
        fields
            .map {
                CheckBoxInputView(context, it, isTenantCompliant)
            }
            .forEach { addView(it) }
    }

    fun observe(onChange: () -> Unit) {
        children.map { it as CheckBoxInputView }.forEach { input ->
            input.observe { onChange() }
        }
    }

    fun getResponses(): List<Pair<String, Boolean>> =
        children
            .map { (it as CheckBoxInputView).validate().isChecked() }
            .toList()
}