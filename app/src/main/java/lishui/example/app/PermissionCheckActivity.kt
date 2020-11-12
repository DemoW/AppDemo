package lishui.example.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import lishui.example.common.UiIntents
import lishui.example.common.util.PermissionUtils

class PermissionCheckActivity : AppCompatActivity() {

    companion object {
        const val REQUIRED_PERMISSIONS_REQUEST_CODE = 1
        const val AUTOMATED_RESULT_THRESHOLD_MILLIS: Long = 250
        const val PACKAGE_URI_PREFIX = "package:"
    }

    private var mRequestTimeMillis: Long = 0
    private var isRedirectOnCreate: Boolean = false

    private lateinit var mRequestView: TextView
    private lateinit var mSettingsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRedirectOnCreate = true
        if (redirectIfNeeded()) return

        setContentView(R.layout.activity_permission_check)

        mRequestView = findViewById<View>(R.id.permission_request) as TextView
        mRequestView.setOnClickListener { tryRequestPermission() }

        mSettingsView = findViewById<View>(R.id.permission_settings) as TextView
        mSettingsView.setOnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse(PACKAGE_URI_PREFIX + packageName)
            )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isRedirectOnCreate && redirectIfNeeded()) return
        if (isRedirectOnCreate) isRedirectOnCreate = false
    }

    private fun tryRequestPermission() {
        val missingPermissions: Array<String> = PermissionUtils.getMissingRequiredPermissions()
        if (missingPermissions.isEmpty()) {
            redirect()
            return
        }
        mRequestTimeMillis = SystemClock.elapsedRealtime()
        requestPermissions(
            missingPermissions,
            REQUIRED_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUIRED_PERMISSIONS_REQUEST_CODE) return

        if (PermissionUtils.hasRequiredPermissions()) {
            //Factory.get().onRequiredPermissionsAcquired()
            redirect()
        } else {
            val currentTimeMillis = SystemClock.elapsedRealtime()
            // If the permission request completes very quickly, it must be because the system
            // automatically denied. This can happen if the user had previously denied it
            // and checked the "Never ask again" check box.
            if (currentTimeMillis - mRequestTimeMillis < AUTOMATED_RESULT_THRESHOLD_MILLIS) {
                mRequestView.visibility = View.GONE
                mSettingsView.visibility = View.VISIBLE
            }
        }
    }

    /** Returns true if the redirecting was performed  */
    private fun redirectIfNeeded(): Boolean {
        if (!PermissionUtils.hasRequiredPermissions()) return false
        redirect()
        return true
    }

    private fun redirect() {
        UiIntents.get().launchMainUiActivity(this)
        finish()
    }
}