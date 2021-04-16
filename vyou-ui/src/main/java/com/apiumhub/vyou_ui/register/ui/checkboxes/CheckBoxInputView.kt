package com.apiumhub.vyou_ui.register.ui.checkboxes

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouCheckBoxInputBinding



internal fun CheckBoxInputView(context: Context, inputField: CheckBoxField) =
    CheckBoxInputView(context).apply { render(inputField) }

internal class CheckBoxInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    val binding = VyouCheckBoxInputBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(inputView: CheckBoxField) {
        binding.checkBox.text = inputView.title.first
    }


}