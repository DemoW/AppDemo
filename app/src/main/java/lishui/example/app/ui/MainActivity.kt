package lishui.example.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lishui.example.app.R
import lishui.example.common.util.UiUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        UiUtils.hideSystemUI(window)
        UiUtils.showCutoutShortEdgesMode(window)
    }
}