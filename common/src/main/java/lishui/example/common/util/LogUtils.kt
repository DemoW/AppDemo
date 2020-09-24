package lishui.example.common.util

import android.util.Log

object LogUtils {

    private const val TAG = "AppDemo"

    fun d(tag: String = "", content: String) {
        if (tag.isEmpty()) {
            Log.d(TAG, content)
        } else {
            Log.d(TAG, "$tag # $content")
        }
    }

    fun i(tag: String = "", content: String) {
        if (tag.isEmpty()) {
            Log.i(TAG, content)
        } else {
            Log.i(TAG, "$tag # $content")
        }
    }

    fun v(tag: String = "", content: String) {
        if (tag.isEmpty()) {
            Log.v(TAG, content)
        } else {
            Log.v(TAG, "$tag # $content")
        }
    }

    fun e(tag: String = "", content: String) {
        if (tag.isEmpty()) {
            Log.e(TAG, content)
        } else {
            Log.e(TAG, "$tag # $content")
        }
    }

    fun e(tag: String = "", content: String, throwable: Throwable?) {
        if (tag.isEmpty()) {
            Log.e(TAG, content, throwable)
        } else {
            Log.e(TAG, "$tag # $content", throwable)
        }
    }
}