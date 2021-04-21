package com.apiumhub.vyou_ui.components.checkboxes

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children
import com.apiumhub.vyou_ui.profile.presentation.TenantCompliant

internal class DynamicCheckBoxesFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun render(fields: List<CheckBoxField>, isTenantCompliant: Boolean = false) {
        fields
            .map { CheckBoxInputView(context, it, isTenantCompliant) }
            .forEach { addView(it) }
    }

    fun getResponses(): List<Pair<String, Boolean>> =
        children
            .map { (it as CheckBoxInputView).validate().isChecked() }
            .toList()
}