package lishui.example.app

import android.app.Application


class DemoApp : Application(), DemoAppComponentFactory.ContextInitializer {

    private var mContextAvailableCallback: DemoAppComponentFactory.ContextAvailableCallback? = null

    override fun onCreate() {
        super.onCreate()
        mContextAvailableCallback?.onContextAvailable(this)
        DependencyImplBackup.register(this)
    }

    override fun setContextAvailableCallback(
        callback: DemoAppComponentFactory.ContextAvailableCallback?
    ) {
        mContextAvailableCallback = callback;
    }

}