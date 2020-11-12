package lishui.example.app.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import lishui.example.common.util.PermissionUtils

/**
 * Created by lishui.lin on 20-9-29
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PermissionUtils.redirectToPermissionCheckIfNeeded(this)) finish()
    }

    companion object {
        fun <T : BaseActivity?> fromContext(context: Context): T {
            return when (context) {
                is BaseActivity -> context as T
                is ContextThemeWrapper -> fromContext<T>((context as ContextWrapper).baseContext)
                else -> throw IllegalArgumentException("Cannot find BaseActivity in parent tree")
            }
        }
    }
}