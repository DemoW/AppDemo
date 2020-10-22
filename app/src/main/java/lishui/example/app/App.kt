package lishui.example.app

import android.app.Application
import lishui.example.common.util.LogUtils

class App : Application(), AppDemoComponentFactory.ContextInitializer {

    private lateinit var mContextCallback: AppDemoComponentFactory.ContextAvailableCallback

    override fun onCreate() {
        super.onCreate()
        if (this::mContextCallback.isInitialized) {
            mContextCallback
                .also { LogUtils.d(javaClass.simpleName, "init Dependency in app with ComponentFactory") }
                .onContextAvailable(this, this)
        } else {
            LogUtils.d(javaClass.simpleName, "init Dependency in app with DependencyImpl.register")
            DependencyImpl.register(this, this)
        }
    }

    override fun setContextAvailableCallback(
        callback: AppDemoComponentFactory.ContextAvailableCallback
    ) {
        mContextCallback = callback
    }

}