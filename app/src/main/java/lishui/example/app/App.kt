package lishui.example.app

import android.app.Application
import lishui.example.common.util.LogUtils
import lishui.example.common.util.Utilities

class App : Application(), AppDemoComponentFactory.ContextInitializer {

    private lateinit var mContextCallback: AppDemoComponentFactory.ContextAvailableCallback

    override fun onCreate() {
        super.onCreate()
        if (Utilities.isAtLeastP())
            mContextCallback
                .also { LogUtils.d(javaClass.simpleName, "init Dependency in app at least P") }
                .onContextAvailable(this, this)
        else {
            LogUtils.d(javaClass.simpleName, "init Dependency in app lower than P")
            DependencyImpl.register(this, this)
        }
    }

    override fun setContextAvailableCallback(
        callback: AppDemoComponentFactory.ContextAvailableCallback
    ) {
        mContextCallback = callback
    }

}