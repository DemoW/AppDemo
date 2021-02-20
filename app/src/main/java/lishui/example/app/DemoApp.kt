package lishui.example.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import lishui.example.common.util.LogUtils

@HiltAndroidApp
class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.d(javaClass.simpleName, "init Dependency in app with DependencyImpl.register")
        DependencyImpl.register(this, this)
    }

}