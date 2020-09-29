package lishui.example.common.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import lishui.example.common.R
import lishui.example.common.UIIntents
import lishui.example.common.util.PermissionUtils

class PermissionCheckActivity : AppCompatActivity() {

    private val REQUIRED_PERMISSIONS_REQUEST_CODE = 1
    private val AUTOMATED_RESULT_THRESHOLD_MILLLIS: Long = 250
    private val PACKAGE_URI_PREFIX = "package:"
    private var mRequestTimeMillis: Long = 0

    private lateinit var mNextView: TextView
    private lateinit var mSettingsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (redirectIfNeeded()) {
            return
        }
        setContentView(R.layout.activity_permission_check)

        mNextView = findViewById<View>(R.id.permission_request) as TextView
        mNextView.setOnClickListener { tryRequestPermission() }

        mSettingsView = findViewById<View>(R.id.permission_settings) as TextView
        mSettingsView.setOnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse(PACKAGE_URI_PREFIX + packageName)
            )
            startActivity(intent)
        }
    }

    private fun tryRequestPermission() {
        val missingPermissions: Array<String> = PermissionUtils.getMissingRequiredPermissions()
        if (missingPermissions.isEmpty()) {
            redirect()
            return
        }
        mRequestTimeMillis = SystemClock.elapsedRealtime()
        requestPermissions(missingPermissions, REQUIRED_PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUIRED_PERMISSIONS_REQUEST_CODE) {
            return
        }

        if (PermissionUtils.hasRequiredPermissions()) {
            //Factory.get().onRequiredPermissionsAcquired()
            redirect()
        } else {
            val currentTimeMillis = SystemClock.elapsedRealtime()
            // If the permission request completes very quickly, it must be because the system
            // automatically denied. This can happen if the user had previously denied it
            // and checked the "Never ask again" check box.
            if (currentTimeMillis - mRequestTimeMillis < AUTOMATED_RESULT_THRESHOLD_MILLLIS) {
                mNextView.visibility = View.GONE
                mSettingsView.visibility = View.VISIBLE
            }
        }
    }

    /** Returns true if the redirecting was performed  */
    private fun redirectIfNeeded(): Boolean {
        if (!PermissionUtils.hasRequiredPermissions()) {
            return false
        }
        redirect()
        return true
    }

    private fun redirect() {
        UIIntents.get().launchMainUiActivity(this)
        finish()
    }
}