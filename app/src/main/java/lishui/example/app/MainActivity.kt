package lishui.example.app

import android.os.Bundle
import lishui.example.app.base.BaseActivity
import lishui.example.common.util.LogUtils
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
        //LogUtils.d(TAG, resources.configuration.toString())
    }

    /**
     * dump info without ancestor info
     * $ adb shell dumpsys activity lishui.example.app.MainActivity [...] s
     */
    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        val isDumpAncestor = args?.let { it.isEmpty() || it.last() != "s" } ?: true
        if (isDumpAncestor) super.dump(prefix, fd, writer, args)
    }
}