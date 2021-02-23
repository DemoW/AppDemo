package lishui.example.app

import android.content.Context
import android.telecom.TelecomManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import lishui.example.common.util.LogUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DemoInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("lishui.example.app", appContext.packageName)
    }

    @Test
    fun testTelecom() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val telecomManager = appContext.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        LogUtils.d(telecomManager.defaultDialerPackage)
    }
}