package lishui.example.app.wrapper

import android.content.pm.PackageManager
import lishui.example.app.Factory

/**
 * Created by lishui.lin on 20-10-13
 */
class PackageManagerWrapper {

    companion object {
        fun get(): PackageManagerWrapper {
            return Factory.get().packageManagerWrapper
        }
    }

    fun hasSystemFeature(): Boolean {
        return getPm().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private fun getPm(): PackageManager {
        val appContext = Factory.get().appContext
        return appContext.packageManager
    }
}