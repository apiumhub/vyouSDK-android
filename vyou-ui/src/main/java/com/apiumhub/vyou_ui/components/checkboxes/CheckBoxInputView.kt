package com.apiumhub.vyou_ui.components.checkboxes

import android.content.Context
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouCheckBoxInputBinding
import com.apiumhub.vyou_ui.extensions.applySpans

internal fun CheckBoxInputView(context: Context, inputField: CheckBoxField, isTenantCompliant: Boolean) =
    CheckBoxInputView(context).apply { render(inputField, isTenantCompliant) }

internal class CheckBoxInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = VyouCheckBoxInputBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var inputField: CheckBoxField

    fun render(inputField: CheckBoxField, isTenantCompliant: Boolean) {
        this.inputField = inputField
        val urlSpan = URLSpan(inputField.url)
        binding.checkBox.text = inputField.title.first.applySpans(
            context, inputField.url, inputField.title.second to urlSpan
        )
        binding.checkBox.movementMethod = LinkMovementMethod.getInstance()
        binding.checkBox.isChecked = isTenantCompliant && inputField.isRequired
    }

    fun isChecked(): Pair<String, Boolean> = inputField.id to binding.checkBox.isChecked

    @Throws(ValidationException::class)
    fun validate(): CheckBoxInputView = apply {
        if (inputField.isRequired && !binding.checkBox.isChecked) {
            binding.checkboxErrorTv.isVisible = true
            throw ValidationException(this)
        } else {
            binding.checkboxErrorTv.isVisible = false
        }
    }
}