package com.apiumhub.vyou_ui.components.list

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_ui.components.FieldOutModel
import com.apiumhub.vyou_ui.register.domain.*

internal class DynamicListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun render(fields: List<InputField>) {
        fields
            .map { createComponent(it.id) }
            .forEach { addView(it) }
    }

    private fun createComponent(it: String): View = FieldOutputView(context, it)

    fun fillWithProfile(profile: VYouProfile) {
        val components = children.map { it as VYouOutputComponent }.groupBy { it.id }
        val validViews = mutableListOf<String>()
        profile.email?.let {
            components["vyou_internal_email"]?.first()?.setValue(it)
            validViews.add("vyou_internal_email")
        }
        profile.dynamicFields.entries.forEach { entry ->
            entry.value?.let {
                if (it.isNotEmpty() && it.isNotBlank()) {
                    components[entry.key]?.first()?.setValue(it)
                    validViews.add(entry.key)
                }
            }
        }
        profile.mandatoryFields.entries.forEach { entry ->
            entry.value?.let {
                if (it.isNotEmpty() && it.isNotBlank()) {
                    components[entry.key]?.first()?.setValue(it)
                    validViews.add(entry.key)
                }
            }
        }
        children.forEach { view ->
            if(!validViews.contains(view.tag)) removeView(view)
        }
        removeView(findViewWithTag("name"))
    }
}