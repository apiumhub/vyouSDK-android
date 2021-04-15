package com.apiumhub.vyou_ui.register.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.apiumhub.vyou_core.tenant.domain.VyouFieldDto

class DynamicFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun setupWithFields(fields: List<VyouFieldDto>) {
        fields
            .map { createComponent(it) }
            .forEach { addView(it) }
    }

    private fun createComponent(it: VyouFieldDto): View {
        if (it.name == "birth") {
            return TODO("Date selector component(it.required)")
        }
        return TODO("Text input(it.type, it.required)")
    }
}