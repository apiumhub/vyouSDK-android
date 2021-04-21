package com.apiumhub.vyou_ui.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

fun String.applySpans(context: Context,
    url: String,
    vararg pairs: Pair<String, Any>
): SpannableStringBuilder {
    val spannable = SpannableStringBuilder(this)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }
    pairs.forEach {
        val start = this.indexOf(it.first)
        val end = start + it.first.length
        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannable
}

fun addLeftIconToTextField(isMandatory: Boolean, view: View,  drawable: Int){
    when (view) {
        is TextView -> {
            when {
                !isMandatory -> view.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                else -> view.setCompoundDrawablesWithIntrinsicBounds(drawable,0,0,0)
            }
        }
        is TextInputLayout -> {
            when {
                !isMandatory -> view.setStartIconDrawable(0)
                else -> view.setStartIconDrawable(drawable)}
        }
    }
}