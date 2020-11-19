package lishui.example.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import lishui.example.app.base.BaseActivity
import lishui.example.common.UiIntents
import lishui.example.common.util.LogUtils
import java.io.FileDescriptor
import java.io.PrintWriter


class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var navFragmentContainer: FragmentContainerView

    private val navDestinationChangedListener = NavDestinationChangedListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        navFragmentContainer = findViewById(R.id.nav_host_fragment)
        val toolbar: Toolbar = findViewById(R.id.app_tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //NavigationUI.setupWithNavController(toolbar, findNavController(R.id.nav_host_fragment))
//        Navigation.findNavController(navFragmentContainer)
//            .addOnDestinationChangedListener(navDestinationChangedListener)
    }

    inner class NavDestinationChangedListener : NavController.OnDestinationChangedListener {

        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            LogUtils.d(
                TAG,
                "onDestinationChanged destination=${destination.label}, id=${destination.id}"
            )
            supportActionBar?.title = destination.label
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.action_search -> {
            UiIntents.get().launchSearch(this)
            true
        }
        R.id.action_settings -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }.also {
        LogUtils.d(TAG, "onOptionsItemSelected id=${item.itemId}, title=${item.title}")
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