package com.apiumhub.vyou_ui.register.ui.checkboxes

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout


internal class DynamicCheckBoxesFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun render(fields: List<CheckBoxField>) {
        fields
            .map { CheckBoxInputView(context, it) }
            .forEach { addView(it) }
    }


}