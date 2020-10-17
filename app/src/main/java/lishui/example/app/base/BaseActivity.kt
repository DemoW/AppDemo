package lishui.example.app.base

import android.os.Bundle
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
}