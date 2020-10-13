package lishui.example.app.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import lishui.example.app.R
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : BaseActivity() {

    companion object {
        private const val MAIN_PAGE_TAG = "main_page_tag"
        private const val CAMERA_PAGE_TAG = "camera_page_tag"
    }

    private lateinit var navFragmentContainer: FragmentContainerView
    private lateinit var homeItemButton: Button
    private lateinit var cameraItemButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)
        initViews()
    }

    private fun initViews() {
        navFragmentContainer = findViewById(R.id.nav_host_fragment)
        homeItemButton = findViewById(R.id.main_home_item)
        cameraItemButton = findViewById(R.id.main_camera_item)

        homeItemButton.setOnClickListener {
            with(navFragmentContainer.findNavController()) {
                if (this.currentDestination?.id == R.id.nav_main_fragment) return@with
                popBackStack()
            }
        }

        cameraItemButton.setOnClickListener {
            with(navFragmentContainer.findNavController()) {
                if (this.currentDestination?.id == R.id.nav_camera_fragment) return@with
                navigate(R.id.action_mainFragment_to_cameraFragment)
            }
        }
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
        val isDumpAncestor = args?.let { it.isEmpty() || it.last() != "s" } ?: true
        if (isDumpAncestor) super.dump(prefix, fd, writer, args)
    }
}