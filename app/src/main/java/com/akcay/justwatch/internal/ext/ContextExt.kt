package com.akcay.justwatch.internal.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

tailrec fun Context.asActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.asActivity()
        else -> null
    }
}
