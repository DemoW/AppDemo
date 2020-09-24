package lishui.example.app

import android.content.Context
import lishui.example.common.util.LogUtils
import java.util.*

/**
 * Created by lishui.lin for AppDemo on 20-9-23
 */
object ProfileProperties {

    private const val PROFILE_NAME = "profile.properties"

    private var properties: Properties = Properties()

    fun init(context: Context) {
        properties.load(context.applicationContext.resources.assets.open(PROFILE_NAME))

        val propertyNames = properties.stringPropertyNames()
        propertyNames.forEach{
            LogUtils.d(content = "propertyName= $it")
        }
    }
}