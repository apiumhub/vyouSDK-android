package com.apiumhub.vyou_ui.components.form

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouDateInputBinding
import com.apiumhub.vyou_ui.extensions.addLeftIconToTextField
import com.apiumhub.vyou_ui.register.domain.DateField
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

internal fun DateInputView(context: Context, inputfield: DateField) =
    DateInputView(context).apply { render(inputfield) }

internal class DateInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), VYouInputComponent {

    private val binding: VyouDateInputBinding =
        VyouDateInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var inputField: DateField

    override val id: String
        get() = inputField.id

    override val isValid: Boolean
        get() = inputField.isRequired || binding.dateTextInputEt.text.toString().isNotEmpty()

    override var visible: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
        }

    override fun setValue(value: String) {
        binding.dateTextInputEt.setText(formatDate(parseIsoDate(value)))
    }

    override fun getKeyValue(): FieldOutModel? =
        binding.dateTextInputEt.text?.takeIf { it.isNotEmpty() }?.let {
            FieldOutModel(inputField.fieldType, inputField.id, parseShortDate(it.toString()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        }

    fun render(inputField: DateField) {
        this.inputField = inputField
        tag = inputField.id
        binding.dateInputLayout.hint = inputField.getTitle(context)
        binding.dateTextInputEt.setOnClickListener { openCalendarComponent() }
        if(inputField.isRequired.not()) {
            binding.dateInputLayout.hint = inputField.getTitle(context) + " (${context.getString(R.string.field_optional)})"
        }

        binding.dateTextInputEt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) runCatching { validate() }
        }
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
                    val date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                    binding.dateTextInputEt.setText(formatDate(date))
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
                binding.dateInputLayout.error = context.getString(R.string.error_field_is_mandatory)
                throw ValidationException(this)
            } else
                binding.dateInputLayout.error = null
        }
    }

    override fun observe(onChange: () -> Unit) {
        binding.dateTextInputEt.addTextChangedListener {
            onChange()
        }
    }

    private fun parseIsoDate(date: String): LocalDateTime =
        LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    private fun parseShortDate(date: String): LocalDateTime =
        LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).atStartOfDay()

    private fun formatDate(date: LocalDateTime) =
        date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
}
