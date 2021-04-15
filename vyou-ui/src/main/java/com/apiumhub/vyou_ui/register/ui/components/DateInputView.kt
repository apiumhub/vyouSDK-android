package com.apiumhub.vyou_ui.register.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.databinding.VyouDateInputBinding
import com.apiumhub.vyou_ui.register.domain.DateField

internal fun DateInputView(context: Context, inputfield: DateField) = DateInputView(context).apply { render(inputfield) }
internal class DateInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouDateInputBinding = VyouDateInputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun getKeyValue(): Pair<String, String> = tag.toString() to binding.dateInputEt.text.toString()

    fun render(inputField: DateField) {
        tag = inputField.id
        binding.dateInputEt.hint = inputField.id
    }
}
