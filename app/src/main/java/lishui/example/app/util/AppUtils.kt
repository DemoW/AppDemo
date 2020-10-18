package lishui.example.app.util

import android.content.Context
import android.content.ContextWrapper
import android.view.ContextThemeWrapper
import lishui.example.app.base.BaseActivity

fun <T : BaseActivity?> fromContext(context: Context): T {
    return when (context) {
        is BaseActivity -> context as T
        is ContextThemeWrapper -> fromContext<T>((context as ContextWrapper).baseContext)
        else -> throw IllegalArgumentException("Cannot find BaseActivity in parent tree")
    }
}