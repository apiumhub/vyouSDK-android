package com.apiumhub.vyou_ui.register.ui.form

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouDateInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.DateField
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException
import java.util.*


internal fun DateInputView(context: Context, inputfield: DateField) =
    DateInputView(context).apply { render(inputfield) }

internal class DateInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VyouInputComponent {

    private val binding: VyouDateInputBinding =
        VyouDateInputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun getKeyValue(): Pair<String, String> =
        tag.toString() to binding.dateTextInputEt.text.toString()

    private lateinit var inputField: DateField

    fun render(inputField: DateField) {
        this.inputField = inputField
        tag = inputField.id
        binding.dateInputLayout.hint = inputField.title
        binding.dateTextInputEt.setOnClickListener { openCalendarComponent() }
        addLeftIconToTextField(
            inputField.isRequired,
            binding.dateInputLayout,
            R.drawable.ic_mandatory_field
        )
    }

    private fun openCalendarComponent() {
        val c: Calendar = Calendar.getInstance()
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val year: Int = c.get(Calendar.YEAR)

        val datePickerDialog =
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val newMonth = month + 1
                    val date = "$dayOfMonth/$newMonth/$year"
                    binding.dateTextInputEt.setText(date)
                },
                year,
                month,
                day
            )
        datePickerDialog.show()
    }

    override fun validate() = apply {
        binding.dateTextInputEt.text?.let {
            if (inputField.isRequired && it.isEmpty()) {
                binding.dateInputLayout.error = "This field is mandatory"
                throw ValidationException(this)
            } else
                binding.dateInputLayout.error = null
        }
    }
}
