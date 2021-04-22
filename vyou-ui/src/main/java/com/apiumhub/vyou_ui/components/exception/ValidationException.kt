package com.apiumhub.vyou_ui.components.exception

import android.view.View

internal class ValidationException(val view: View) : Exception("Validation failed for view: $view")