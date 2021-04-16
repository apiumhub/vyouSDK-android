package com.apiumhub.vyou_ui.register.ui.checkboxes

import android.content.Context
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouCheckBoxInputBinding
import com.apiumhub.vyou_ui.extensions.applySpans


internal fun CheckBoxInputView(context: Context, inputField: CheckBoxField) =
    CheckBoxInputView(context).apply { render(inputField) }

internal class CheckBoxInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = VyouCheckBoxInputBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(inputView: CheckBoxField) {
        val urlSpan = URLSpan(inputView.url)
        binding.checkBox.text = inputView.title.first.applySpans(
            context, inputView.url, inputView.title.second to urlSpan
        )
        binding.checkBox.movementMethod = LinkMovementMethod.getInstance()
    }

    fun isChecked() = binding.checkBox.isChecked
}