package lishui.example.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import lishui.example.app.R
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : BaseActivity() {

    companion object {
        const val MAIN_PAGE_TAG = "main_page_tag"
    }

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.findFragmentByTag(MAIN_PAGE_TAG)?.let {
                mainFragment = it as MainFragment
            }

            if (!this::mainFragment.isInitialized) mainFragment = MainFragment.newInstance()
            switchFragment(mainFragment)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTag = when (fragment) {
            is MainFragment -> {
                MAIN_PAGE_TAG
            }
            else -> {
                ""
            }
        }

        // other fragments hide
        if (fragment.isAdded)
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commitNow()
        else
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragmentTag)
                .show(fragment)
                .commitNow()
    }

    /**
     * dump info without ancestor info
     * $ adb shell dumpsys activity lishui.example.app.ui.MainActivity [...] s
     */
    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        val isDumpSimply = args?.let { it.isNotEmpty() && it.last() != "s" } ?: true
        if (isDumpSimply) super.dump(prefix, fd, writer, args)

        mainFragment?.dumpInfo(writer)
    }
}