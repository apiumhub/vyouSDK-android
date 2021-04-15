package com.apiumhub.vyou_ui.register.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouTextInputBinding
import com.apiumhub.vyou_ui.register.domain.TextField

internal fun TextInputView(context: Context, inputField: TextField) = TextInputView(context).apply { render(inputField) }

internal class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouTextInputBinding = VyouTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun getKeyValue(): Pair<String, String> = tag.toString() to binding.textInputEt.text.toString()

    fun render(inputField: TextField) {
        tag = inputField.id
        binding.inputLayout.hint = inputField.id
    }
}